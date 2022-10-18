package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.*;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.utils.*;
import com.yiie.vo.data.*;
import com.yiie.vo.request.*;
import com.yiie.vo.data.GymPath;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "组织模块-场馆管理")
@RequestMapping("/sys")
@RestController
@CrossOrigin//图片上传OSS跨域用的
public class gymController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;

    @Autowired
    private GymOrderService gymOrderService;
    @Autowired
    private GymHistoryService gymHistoryService;

    @Autowired
    private GymOpenTimeService gymOpenTimeService;

    @ApiOperation("一键审批")
    @PostMapping("/autopass")
    public DataResult autoPass(HttpServletRequest request){
        String id=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId=userService.getDeptIdFromUserId(id);
        gymService.autoPassBydeptId(deptId);
        return DataResult.success();
    }

    @ApiOperation("查询Gym图片路径")
    @GetMapping("/gym/getPicture/{gymId}")
    public ResponseEntity<Object> getCollectionBook(HttpServletRequest request, @PathVariable("gymId") String gymId) {
        System.out.print("查询Gym："+gymId+"\n");
        String path=gymService.getById(gymId).getGymPicturesPath();
        System.out.print("图片路径："+path+"\n");
        List<GymPath> gymPaths=new ArrayList<>();
        int index=1;
        while(index<=Constant.gym_pictureNum){
            GymPath gymPath=new GymPath(gymId,index,Constant.gym_defaultPicture);
            System.out.print("GymPath："+gymPath.toString()+"\n");
            if(path.length()>0){
                String p=path.substring(0,path.indexOf(";"));
                System.out.print("p："+p+"\n");
                gymPath.setPath(p);//修改地址
                path=path.substring(path.indexOf(";")+1);
            }
            gymPaths.add(gymPath);
            index++;
        }
        /*while(path.length()>0){
            String p=path.substring(0,path.indexOf(";"));
            GymPath gymPath=new GymPath(id,p);
            System.out.print("GymPath："+gymPath.toString()+"\n");
            gymPaths.add(gymPath);
            id++;
            path=path.substring(path.indexOf(";")+1);
        }*/
        return new ResponseEntity<>(gymPaths, HttpStatus.OK);
    }

    @GetMapping("/containData/{gymname}")
    public DataResult<ContainData> test2(@PathVariable("gymname") String name) throws ParseException {
        System.out.print("测试\n");
        ContainData containData=new ContainData();
        List<Gym> gymList=gymService.getByName(name);
        Gym gym;
        if(gymList.size()>0){
            gym=gymList.get(0);//取第一个
        }else {//不存在场馆
            return DataResult.success(null);
        }
        String gymName=gym.getGymName();//场馆名称
        String gymId=gym.getGymId();//场馆Id
        List<SportAndValue> ageList = new ArrayList<>();

        //年龄段数据获取
        {
            List<String> peopleIdentityCard = gymHistoryService.getIdentity(gymId);//获取历史运动人的身份证
            SportAndValue old = new SportAndValue("老年人", 0);//50以上
            SportAndValue adult = new SportAndValue("成年人", 0);//18-50
            SportAndValue teenage = new SportAndValue("青少年", 0);//11-18
            SportAndValue child = new SportAndValue("小孩", 0);//0-10
            //人数设置
            for (int i = 0; i < peopleIdentityCard.size(); i++) {
                int year = TimeUtile.IDcard(peopleIdentityCard.get(i));//获取出生年
                Calendar date = Calendar.getInstance();
                String nowYear = String.valueOf(date.get(Calendar.YEAR));
                int age = Integer.parseInt(nowYear) - year;//获取年龄
                if (age < 10) {
                    child.setValue(child.getValue() + 1);
                } else if (age >= 10 && age < 18) {
                    teenage.setValue(teenage.getValue() + 1);
                } else if (age >= 18 && age < 50) {
                    adult.setValue(adult.getValue() + 1);
                } else {
                    old.setValue(old.getValue() + 1);
                }
            }
            //添加数据
            ageList.add(old);
            ageList.add(adult);
            ageList.add(teenage);
            ageList.add(child);
        }

        //运动人数获取
        List<SportAndValue> evList=gymHistoryService.getTypeAndValue(gymId);

        //获取场地预约时间
        List<Integer> timeList;
        {
            int[] hourTime=new int[11];//一共11个时间段,8:00-18:00
            List<DateSpan> dateSpans=gymHistoryService.getOrderDateSpan(gymId);
           /* for(DateSpan d:dateSpans){
                System.out.print("ds:"+d.toString()+"\n");
            }*/
            for(int i=0;i<dateSpans.size();i++){
                Calendar calendar = Calendar.getInstance();
                DateSpan dateSpan=dateSpans.get(i);
                calendar.setTime(dateSpan.getStart());
                int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
                calendar.setTime(dateSpan.getEnd());
                int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
//                System.out.print("ordertime:"+start+"-"+end+"\n");
                for(int j=Math.max(8,start);j<=Math.min(end,18);j++){//从8开始,18结束
                    hourTime[(j-8)]++;//因为从8点开始计数
                }
            }
//            timeList = (List<Integer>) Arrays.asList(hourTime);//转换为list
            timeList = Arrays.stream(hourTime).boxed().collect(Collectors.toList());;//转换为list
        }

        //获取每个人的运动时长，并从中获取场馆当日每个时段的人数
        List<Integer> pTimeList;
        List<SportAndValue> pst=new ArrayList<>(),pst2;
        List<PeopleSportTime> peopleSportTimes=gymHistoryService.getPeopleSportTimes(gymId);//人的运动时长
        Map<String,Integer> pt=new HashMap<>();
        Map<String,String> idAndName=new HashMap<>();
        {
            int[] pTime=new int[11];//一共11个时间段,8:00-18:00
            for(PeopleSportTime p:peopleSportTimes){
//                System.out.print("p:"+p.toString()+"\n");
                String pid=p.getIdcard();
                String pname=p.getName();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(p.getStart());
                int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
                calendar.setTime(p.getEnd());
                int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
                for(int t=Math.max(8,start);t<=Math.min(18,end);t++){//防止越界添加最大最小
                    pTime[t-8]++;
                }
                int hours=end-start;
                pt.put(pid,pt.getOrDefault(pid,0)+hours);//根据id更新数值
                idAndName.put(pid,pname);//每个id对应的名字
            }
            ///................处理Map得到所需数据
            for(String identity:pt.keySet()){
                SportAndValue s=new SportAndValue(idAndName.get(identity),pt.get(identity));//名字与数值
//                System.out.print("s:"+s.toString()+"\n");
                pst.add(s);
            }
            //根据时长排序
            Collections.sort(pst, new Comparator<SportAndValue>() {
                @Override
                public int compare(SportAndValue o1, SportAndValue o2) {
                    return o1.getValue()-o2.getValue();
                }
            });
            pst2=pst.subList(0,Math.min(5,pst.size()));//运动排行榜，选取至多5个
//            pTimeList=Arrays.asList(pTime);//11个时段的人数
            pTimeList=Arrays.stream(pTime).boxed().collect(Collectors.toList());;//11个时段的人数
        }

        //获取近一个月各场馆的人员数量
        List<GymPeopleMonth> gpm;
        {
            Date monthAgo=TimeUtile.stepMonth(new Date(),-1);//以当前时间为基准计算一个月前的时间
            Date todayTime=TimeUtile.toIntegral(TimeUtile.stepDay(new Date(),1));//纯做测试用
            gpm=gymHistoryService.getPeopleNumMonth(monthAgo,todayTime);
        }

        //近五天的场馆的实际开放时长和预计开放时长
        List<GymOpenTime> gymOpenTimes;
        int[][] openTime;
        List<GymCloseTime> gymCloseTimes;
        List<Integer> except=new ArrayList<>();
        List<Integer> actual=new ArrayList<>();
        List<String> dates;
        {
            //获取整点
            Date date1=new Date();
            int weekDay=TimeUtile.getWeekDay(date1)-1;//获取昨天的星期
            Date date2= TimeUtile.stepDay(date1,-5);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df3 = new SimpleDateFormat("MM-dd");//获取月日
            //五天的日期：月.日
            dates=new ArrayList<>();
            int datesI=0;
            Date today=df2.parse(df.format(date1));
            Date fiveDaysAgo=df2.parse(df.format(date2));
            //日期要获取明天的年月日与五天前的年月日，时间都设置为0：0：0即整点
            //每个场馆的开放时间
            gymOpenTimes=gymService.getGymOT();

            //处理数据，计算每个每个场馆近五天每天开放时长
            openTime=new int[5][2];//近五天所有场馆的预期开放与实际开放累积存储
            Arrays.fill(openTime,new int[]{0,0});//初始化
            for(int i=0;i<gymOpenTimes.size();i++){
                String sG=gymOpenTimes.get(i).getGymId();
                //该场馆近五天的闭馆时间:按whichDay时间降序，故一个一个向后判断即可
                gymCloseTimes=gymOpenTimeService.getGymCT(sG,today,fiveDaysAgo);
                int index=0;
                for(int j=0;j<5;j++){//五天
                    Date agoDay=TimeUtile.stepDay(today,-(j+1));//往前(j+1)天计算日期
                    if(datesI<5){//填充日期数组
                        dates.add(df3.format(agoDay).replace("-","."));//月.日
                        datesI++;
                    }
                    agoDay=TimeUtile.toIntegral(agoDay);//设为整点
                    Date closeDay=null;
                    if(weekDay<0){//判断星期
                        weekDay=6;
                    }
                    String h1,h2,h3,h4;
                    //获取时间段
                    if(weekDay!=0){//工作日1-6
                        String workDay=gymOpenTimes.get(i).getMonday();
//                        System.out.print("workDay:"+workDay+"\n");
                        h1=workDay.substring(0,workDay.indexOf("-"));
                        h2=workDay.substring(workDay.indexOf("-")+1);
                        workDay=workDay.substring(workDay.indexOf(" ")+1);//获取下半场
                        h3=workDay.substring(0,workDay.indexOf("-"));
                        h4=workDay.substring(workDay.indexOf("-")+1);
                    }else {//周日
                        String weekend=gymOpenTimes.get(i).getSaturday();
                        h1=weekend.substring(0,weekend.indexOf("-"));
                        h2=weekend.substring(weekend.indexOf("-")+1);
                        weekend=weekend.substring(weekend.indexOf(" ")+1);//获取下半场
                        h3=weekend.substring(0,weekend.indexOf("-"));
                        h4=weekend.substring(weekend.indexOf("-")+1);
                    }
                    String closeTime="23:59";//初始化为最后一分钟
                    if(index<gymCloseTimes.size()){//存在闭馆时间
                        closeDay=gymCloseTimes.get(index).getWhichDay();//获取当天日期
                        closeDay=TimeUtile.toIntegral(closeDay);
                    }
                    float differ1,differ2,differ3,differ4;
                    //计算预计开放
                    differ3=TimeUtile.getHourDiffer(h1,h2,closeTime);
                    differ4=TimeUtile.getHourDiffer(h3,h4,closeTime);
                    //计算实际开放
                    //考虑到可能出现缺少闭馆数据
                    if(closeDay!=null&&agoDay==closeDay){
                        closeTime=gymCloseTimes.get(index).getCloseTime();//获取该日闭馆时间
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                        index++;//该日期被用掉了
                    }else {//否则用默认
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                    }
                    weekDay--;//星期向前走一天
                    openTime[i][0]+=differ3+differ4;//累计预计时间
                    openTime[i][1]+=differ1+differ2;//累计实际时间
                }
            }
            //处理openTime，产出三个数组，预期时长，实际时长，日期数组
            for(int dateT=0;dateT<openTime.length;dateT++){
                except.add(openTime[dateT][0]);
                actual.add(openTime[dateT][1]);
            }
            //最终返回except,actual,dates
        }

        //重新定义返回的数据模型
        DataResult<ContainData> result = DataResult.success();
        {
            containData.setGymId(gymId);
            containData.setGymName(gymName);
            containData.setAgeList(ageList);
            containData.setEvList(evList);
            containData.setOrderTimeList(timeList);
            containData.setSportRank(pst2);
            containData.setTodayPeoples(pTimeList);
            containData.setLastMonthPeople(gpm);
            containData.setOpenTime(dates);
            containData.setExceptOpenTime(except);
            containData.setActualOpenTime(actual);
            result.setData(containData);
        }
        System.out.print("containData--------------------\n");
        System.out.print(containData.toString());
        return result;
    }
    /*@PostMapping("/loadGymPicture")
    public DataResult loadGP(@RequestParam("picture") MultipartFile[] files,@RequestParam("gymId") String gymId,@RequestParam("pictureId") int id) throws IOException {
        System.out.print("图片存储id-gymId:"+"\n");
        System.out.print(id+"-"+gymId+"\n");
        Gym gym=gymService.getById(gymId);
        String path=gym.getGymPicturesPath();
        System.out.print("已有路径:"+path+"\n");

        List<String> existence=new ArrayList<>();
        while(path.length()>0){
            existence.add(path.substring(0,path.indexOf(";")));
            path=path.substring(path.indexOf(";")+1);
        }
        if(existence.size()>0){
            System.out.print("当前已存在路径：\n");
            for(String s:existence){
                System.out.print(s+"\n");
            }
        }

        for(MultipartFile file:files){
            if(file.isEmpty())
                return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
            if(id>3){
                System.out.print("已存满，直接返回\n");
                break;
            }
            System.out.print("图片非空\n");
            OSS oss=new OSS();
            String picPath=oss.gymPictureLoad(file);
            System.out.print("图片存储地址:"+path+"\n");
            if(id-1>=existence.size()){
                existence.add(picPath);
            }else
                existence.set(id-1,picPath);//根据id替换新地址
            id++;
        }
        String newPath="";
        for(String s:existence){
            newPath+=s+";";
        }
        //路径更新
        GymUpdateReqVO gymUpdateReqVO=new GymUpdateReqVO();
        BeanUtils.copyProperties(gym,gymUpdateReqVO);
        gymUpdateReqVO.setGymPicturesPath(newPath);
        System.out.print("更新存储:"+newPath+"\n");
        gymService.updateGymInfo(gymUpdateReqVO);
        return DataResult.success();
    }
*/
    @GetMapping("/containData2/{gymname}")
    public DataResult<DataList> test3(@PathVariable("gymname") String name){
        System.out.print("请求数据................\n");
        System.out.print("场馆名称:"+name+"\n");
        Gym gym=gymService.getById(name);
        if(gym==null){
            System.out.print("直接返回\n");
            return DataResult.success(null);
        }
        DataList dataList=new DataList();
        DataResult<DataList> result=DataResult.success();

        //预约人数：
        List<SportAndValue> quan1=gymOrderService.getTypeAndValue(name);
        List<SportAndValue> quan2=gymHistoryService.getTypeAndValue(name);
        //这个图不知道怎么获取
        List<Integer> quan3=new ArrayList<>();
        //排行榜，从orderdetail里面根据identity找到用户，统计该用户运动时长？
        List<SportRank> quan4=new ArrayList<>();
        //日期
        List<Integer> quan5=new ArrayList<>();

        //所有场馆数据统计
        List<SportAndValue> quan6=gymOrderService.getAllTypeAndValue();
        //统计order不同gymId的数据条数
        List<SportAndValue> quan7=gymOrderService.getGymPeopleNum();
        for(int i=0;i<quan7.size();i++){
            SportAndValue s=quan7.get(i);
            s.setSport("gym"+(i+1));
            quan7.set(i,s);
        }

        //只有计划。没有实际
        List<Integer> quan8=new ArrayList<>();
        /*quan1.add(new SportAndValue("运动1",10));
        quan1.add(new SportAndValue("运动2",20));
        quan1.add(new SportAndValue("运动3",30));
        quan1.add(new SportAndValue("运动4",40));*/

        /*quan2.add(new SportAndValue("运动1",10));
        quan2.add(new SportAndValue("运动2",20));
        quan2.add(new SportAndValue("运动3",30));
        quan2.add(new SportAndValue("运动4",40));*/
        quan4.add(new SportRank("运动1",10));
        quan4.add(new SportRank("运动2",20));
        quan4.add(new SportRank("运动3",30));
        quan4.add(new SportRank("运动4",40));

        for(int i=0;i<11;i++){
            quan3.add((int) (Math.random() * 100));
        }
        for(int i=0;i<10;i++){
            quan5.add((int) (Math.random() * 500));
        }/*
        for(int i=0;i<4;i++){
            quan7.add((int) (Math.random() * 80));
        }*/
        for(int i=0;i<4;i++){
            quan8.add((int) (Math.random() * 10));
        }
        dataList.setQuan1(quan1);
        dataList.setQuan2(quan2);
        dataList.setQuan3(quan3);
        dataList.setQuan4(quan4);
        dataList.setQuan5(quan5);
        dataList.setQuan6(quan6);
        dataList.setQuan7(quan7);
        dataList.setQuan8(quan8);
        dataList.setQuan9(quan8);
        dataList.setGymName("XXX场馆");
        result.setData(dataList);
        return result;
    }

    @PostMapping("/H5Gyms")
    @ApiOperation(value = "H5场地信息接口")
    @LogAnnotation(title = "场馆管理",action = "分页获取场地列表")
    // @RequiresPermissions("sys:gym:list")
    // interface for h5, testing...
    public DataResult<PageVO<Gym>> h5PageInfo(@RequestBody GymPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<Gym>> h5Result= DataResult.success();
        h5Result.setData(gymService.h5PageInfo(vo));
        return h5Result;
    }

    @PostMapping("/H5SearchGyms")
    @ApiOperation(value = "H5场地信息接口")
    @LogAnnotation(title = "场馆管理",action = "分页获取场地列表")
    public DataResult<PageVO<Gym>> h5GymSearch(@RequestBody GymSearchNamePageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<Gym>> h5Result= DataResult.success();
        // System.out.println(gymName);
        h5Result.setData(gymService.h5GymSearch(vo));
        return h5Result;
    }


    @PostMapping("/gyms")
    @ApiOperation(value = "场地信息接口")
    @LogAnnotation(title = "场馆管理",action = "分页获取场地列表")
    @RequiresPermissions("sys:gym:list")
    public DataResult<PageVO<Gym>> pageInfo(@RequestBody GymPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<Gym>> result= DataResult.success();
        // we need to set the deptID
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // get deptId, database will get information that belongs to this deptID.
        String deptID = userService.getDeptIdFromUserId(userId);
        // System.out.println(">>>>>>>>>>>>>>>>>>>>" + deptID + "<<<<<<<<<<<<<<<<");
        vo.setDeptId(deptID);
        result.setData(gymService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gym")
    @ApiOperation(value = "删除场地接口")
    @LogAnnotation(title = "场馆管理", action = "删除场地")
    @RequiresPermissions("sys:gym:deleted")
    public DataResult deletedGyms(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymService.deletedGyms(userIds);
        return DataResult.success();
    }

    @PostMapping("/gym")
    @ApiOperation(value = "新增场地接口")
    @LogAnnotation(title = "场馆管理",action = "新增场地")
    @RequiresPermissions("sys:gym:add")
    public DataResult addGym(@RequestBody @Valid GymAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // NOTICE: the deptId should belong to ...
        gymService.addGym(vo);
        return DataResult.success();
    }

    @PutMapping("/gym")
    @ApiOperation(value = "更新场地信息接口")
    @LogAnnotation(title = "场馆管理",action = "更新场地信息")
    @RequiresPermissions("sys:gym:update")
    public DataResult updateGymInfo(@RequestBody @Valid GymUpdateReqVO vo, HttpServletRequest request){
        gymService.updateGymInfo(vo);
        return DataResult.success();
    }

}
