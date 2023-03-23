package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.*;
import com.yiie.constant.Constant;
import com.yiie.entity.Dept;
import com.yiie.entity.ExerciseType;
import com.yiie.entity.Gym;
import com.yiie.entity.GymOpenTime;
import com.yiie.enums.BaseResponseCode;
import com.yiie.utils.*;
import com.yiie.vo.data.*;
import com.yiie.vo.request.*;
import com.yiie.vo.data.GymPath;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
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
    @Autowired
    private DeptService deptService;

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private CustomerService customerService;

    @ApiOperation("查询场馆")
    @GetMapping("/getGymListByDept")
    public DataResult getGymListByDept(HttpServletRequest request){
        System.out.print("查询场馆列表\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        List<Gym> list;
        if(deptId!=null&&deptId.length()!=0&&deptId!=""){
            list=gymService.getGymByDeptId(deptId);
        }else {
            list=gymService.getAll();
        }
        return DataResult.success(list);
    }

    @ApiOperation("查询运动类型")
    @GetMapping("/getExerciseType")
    public DataResult searchExerciseType(){
        System.out.print("查询运动类型\n");
        List<ExerciseType> list=exerciseTypeService.getAllType();
        return DataResult.success(list);
    }
    @ApiOperation("查询全部运动类型")
    @GetMapping("/getExerciseTypeWithOutDeleted")
    public DataResult searchExerciseTypeAll(){
        System.out.print("查询运动类型\n");
        List<ExerciseType> list=exerciseTypeService.getAllTypeWithOutDeleted();
        return DataResult.success(list);
    }

    @ApiOperation("查询场馆名称")
    @PostMapping("/searchGymName")
    public DataResult searchGymName(){
        System.out.print("查询场馆名称\n");
        return DataResult.success();
    }


    @ApiOperation("一键审批")
    @PostMapping("/autopass")
    public DataResult autoPass(HttpServletRequest request){
        String id=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("userId:"+id+"\n");
        String deptId=userService.getDeptIdFromUserId(id);
        gymService.autoPassBydeptId(deptId);
        return DataResult.success();
    }

    @ApiOperation("闭馆")
    @PostMapping("/closeGym")
    public DataResult colseGym(HttpServletRequest request,@RequestBody List<String> id) throws ParseException {
        GymOpenTime gymOpenTime=new GymOpenTime();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        Date date=new Date();
        Date today=TimeUtile.toIntegral(date);//整点:获取年月日
        String time=format.format(date);//获取小时与分钟
        GymOpenTime is=gymOpenTimeService.getByIdAndDay(id.get(0),today);
        if(is!=null){
            return DataResult.getResult(BaseResponseCode.CLOSE);
        }

        String OTId=UUID.randomUUID().toString();
        gymOpenTime.setOpenTimeId(OTId);
        gymOpenTime.setGymId(id.get(0));
        gymOpenTime.setDeptId(id.get(1));
        gymOpenTime.setWhichDay(today);
        gymOpenTime.setCloseTime(time);
        gymOpenTime.setDeleted(1);
        System.out.print("gymOpenTime:"+gymOpenTime.toString()+"\n");
        gymOpenTimeService.addCloseTime(gymOpenTime);
        return DataResult.success("关闭成功");
    }

    @ApiOperation("查询Gym图片路径")
    @GetMapping("/gym/getPicture/{gymId}")
    public ResponseEntity<Object> getCollectionBook(HttpServletRequest request,@PathVariable("gymId") String gymId) {
        System.out.print("查询Gym："+gymId+"\n");
        String path=gymService.getById(gymId).getGymPicturesPath();
        if(path==null)
            path="";
//        System.out.print("图片路径："+path+"\n");
        List<GymPath> gymPaths=new ArrayList<>();
        int index=1;
        while(index<=Constant.gym_pictureNum){
//            GymPath gymPath=new GymPath(gymId,index,Constant.gym_defaultPath+Constant.gym_defaultPicture);
            GymPath gymPath=new GymPath(gymId,index,Constant.gym_defaultPictureBase64);
//            System.out.print("GymPath："+gymPath.toString()+"\n");
            if(path.length()>0){
//                String p=path.substring(0,path.indexOf(";"));
                String p=path.substring(0,path.indexOf("|"));
//                System.out.print("p："+p+"\n");
//                gymPath.setPath(Constant.gym_defaultPath+p);//修改地址
//                gymPath.setPath("/var/bgman/upload/"+p);//修改地址
                gymPath.setPath(p);//修改地址
//                path=path.substring(path.indexOf(";")+1);
                path=path.substring(path.indexOf("|")+1);
            }
            gymPaths.add(gymPath);
            index++;
        }
//        System.out.print("gymPaths:"+gymPaths+"\n");
        return new ResponseEntity<>(gymPaths, HttpStatus.OK);
    }

    @GetMapping("/containData/getAllData/{deptName}")
    public DataResult<ContainData> getAllData(@PathVariable("deptName") String deptName,HttpServletRequest request) throws ParseException {
        System.out.print("\n\n 测试:机关单位数据:"+deptName+"\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String UdeptId=userService.getDeptIdFromUserId(userId);//用户的deptId
        String deptId;//对应的机关单位Id
        List<String> deptsName=new ArrayList<>();;//搜索框内容
        //机关单位
        List<Dept> depts=deptService.getAllDept();
        //先判断是否为机关单位
        System.out.print("\n\n 测试:UdeptId:"+UdeptId+"\n\n");
        if(UdeptId==null||UdeptId.length()==0){//教体局无deptId
            System.out.print("\n\n 添加所有数据\n\n");
            deptsName.add("全部");
            //判断搜索对应的机关单位
            if(deptName.equals("全部")){
                deptId=null;
            }else {
                deptId=null;
                Dept dept=deptService.getByName(deptName);
                if(dept!=null){
                    String di=dept.getId();
                    if(di==null||di.length()==0)
                        return DataResult.success(null);
                    else {
                        deptId=di;//赋值
                    }
                }
            }
            for(Dept d:depts){
                deptsName.add(d.getName());//存入搜索内容
            }
        }else {//机关单位搜索
            deptId=UdeptId;
            deptName=deptService.getById(deptId).getName();
            deptsName.add(deptService.getById(deptId).getName());
        }

        System.out.print("\n\n\ndeptId:"+deptId+"\n");
        System.out.print("\n\n\ndeptNames:"+deptsName+"\n");

        ContainData containData=new ContainData();
        //运动人数获取+所有场馆运动项目占比
        List<ExerciseType> exerciseTypes;


        List<String> allGymEx;
        if(deptId==null){
            exerciseTypes=exerciseTypeService.getAllTypeWithOutDeleted();
            allGymEx=gymService.getAllTypes();
        } else{
            exerciseTypes=exerciseTypeService.getTypeByDeptId(deptId);
            allGymEx=gymService.getTypesByDeptId(deptId);
        }
        List<SportAndValue> allGymSport=new ArrayList<>();
        Map<String, String> exercise=new HashMap<>();
        for(ExerciseType e:exerciseTypes){
            exercise.put(e.getTypeId(),e.getTypeName());
        }
        {
            Map<String, Integer> map1=new HashMap<>();
            for(String as:allGymEx){
                String[] sL=as.split("-");
                for(String ss:sL)
                    map1.put(ss,map1.getOrDefault(ss,0)+1);
            }
            for(String key2:map1.keySet()){
                SportAndValue sportAndValue=new SportAndValue();
                if(exercise.get(key2)!=null&&exercise.get(key2).length()!=0){
                    sportAndValue.setSport(exercise.get(key2));
                    sportAndValue.setValue(map1.get(key2));
                    allGymSport.add(sportAndValue);
                }
            }
        }
        //获取近一个月各场馆的人员数量
        List<GymPeopleMonth> gpm;
        {
            Date monthAgo=TimeUtile.stepMonth(new Date(),-1);//以当前时间为基准计算一个月前的时间
            Date todayTime=TimeUtile.toIntegral(TimeUtile.stepDay(new Date(),1));//纯做测试用
            if(deptId==null)
                gpm=gymHistoryService.getPeopleNumMonth(monthAgo,todayTime);
            else
                gpm=gymHistoryService.getPeopleNumMonthByDeptId(monthAgo,todayTime,deptId);
        }
        //近五天的场馆的实际开放时长和预计开放时长
        List<GymOpenTimeVO> gymOpenTimes;
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
            if(deptId==null)
                gymOpenTimes=gymService.getGymOT();
            else
                gymOpenTimes=gymService.getGymOTByDeptId(deptId);
            //处理数据，计算每个每个场馆近五天每天开放时长
//            System.out.print("gymOpenTimes:"+gymOpenTimes.size()+"\n");
            openTime=new int[5][2];//近五天所有场馆的预期开放与实际开放累积存储
            for(int dateT=0;dateT<openTime.length;dateT++){
                openTime[dateT][0]=0;
                openTime[dateT][1]=0;
            }
//            Arrays.fill(openTime,new int[]{0,0});//初始化,这里不能这样,会导致时间段完全一样,因为是用的引用地址
//            System.out.print("近五天的场馆的实际开放时长和预计开放时长\n");
            for(int i=0;i<gymOpenTimes.size();i++){
                String sG=gymOpenTimes.get(i).getGymId();
//                System.out.print("gymOpenTimes:"+i+"  id:"+gymOpenTimes.get(i).getGymId()+"\n");
                //该场馆近五天的闭馆时间:按whichDay时间降序，故一个一个向后判断即可
                if(deptId==null)
                    gymCloseTimes=gymOpenTimeService.getGymCT(sG,today,fiveDaysAgo);
                else
                    gymCloseTimes=gymOpenTimeService.getGymCT2(sG,today,fiveDaysAgo,deptId);
                int index=0;
                for(int j=0;j<5;j++){//五天
//                    System.out.print("五天:"+j+"\n");
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
                        if(workDay==""||workDay==null||workDay.length()==0||workDay.contains("null"))
                            workDay="9:00-11:30 13:30-17:00";//默认时长
//                        System.out.print("\nworkDay:"+workDay+"\n");
                        h1=workDay.substring(0,workDay.indexOf("-"));
                        h2=workDay.substring(workDay.indexOf("-")+1);
                        workDay=workDay.substring(workDay.indexOf(" ")+1);//获取下半场
                        h3=workDay.substring(0,workDay.indexOf("-"));
                        h4=workDay.substring(workDay.indexOf("-")+1);
                    }else {//周日
                        String weekend=gymOpenTimes.get(i).getSaturday();
//                        System.out.print("weekend:\n\n"+weekend+"\n\n");
                        if(weekend==""||weekend==null||weekend.length()==0||weekend.contains("null")){
                            weekend="9:00-11:30 13:30-19:00";//默认时长
                        }
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
                    if(closeDay==null){
//                        System.out.print("第"+i+"天的第"+j+"个体育馆closeDay缺失");
                    }else {
//                        System.out.print("agoDay  :"+agoDay+"\ncloseDay:"+closeDay+"  "+(agoDay.toString()).equals(closeDay.toString())+"\n\n");
                    }
                    if(closeDay!=null&&(agoDay.toString()).equals(closeDay.toString())){
                        closeTime=gymCloseTimes.get(index).getCloseTime();//获取该日闭馆时间
//                        System.out.print("闭馆时间:"+closeTime+"\n");
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                        index++;//该日期被用掉了
                    }else {//否则用默认
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                    }
                    weekDay--;//星期向前走一天
//                    System.out.print("differ3-differ4-differ1-differ2:"+differ3+"-"+differ4+"-"+differ1+"-"+differ2+"\n");
                    openTime[j][0]+=differ3+differ4;//累计预计时间
                    openTime[j][1]+=differ1+differ2;//累计实际时间
                }
            }
            //处理openTime，产出三个数组，预期时长，实际时长，日期数组
            for(int dateT=0;dateT<openTime.length;dateT++){
//                System.out.print("最终openTime:"+openTime[dateT][0]+"-"+openTime[dateT][1]+"\n");
                except.add(openTime[dateT][0]);
                actual.add(openTime[dateT][1]);
            }
            //最终返回except,actual,dates
        }
        //重新定义返回的数据模型
        DataResult<ContainData> result = DataResult.success();
        {
            containData.setGymName(deptName);
            containData.setLastMonthPeople(gpm);
            containData.setAllGymSport(allGymSport);
            containData.setOpenTime(dates);
            containData.setExceptOpenTime(except);
            containData.setActualOpenTime(actual);
            //加入所有机关单位名称
            containData.setAllGymName(deptsName);
            result.setData(containData);
        }
        System.out.print("containData--------------------\n");
        System.out.print(containData.toString());
        return result;

    }

    @GetMapping("/containData/{gymname}")
    public DataResult<ContainData> getGymData(@PathVariable("gymname") String name,HttpServletRequest request) throws ParseException {
        System.out.print("单场馆数据:"+name+"\n");
        ContainData containData=new ContainData();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String UdeptId= userService.getDeptIdFromUserId(userId);
        Gym gym=null;
        List<Gym> gymList;//获取场馆
        if(UdeptId!=null&&UdeptId.length()>0){
            //第一遍用名字和deptId搜
           gymList=gymService.getByName(name,UdeptId);
           System.out.print("\n\n\ngymList:"+gymList+"\n\n\n");
            //若空，再单独用deptId搜
            if(gymList.size()==0){//搜索为空即初始情况
                gymList=gymService.getByDeptId(UdeptId);
                if(gymList.size()>0)
                    gym=gymList.get(0);//取机关单位的第一个
                else
                    return DataResult.success(null);
            }else {
                gym=gymList.get(0);//取机关单位的第一个
            }
        }else {
            if(name.equals("无"))
                gymList=gymService.getAll();
            else
                gymList=gymService.getAllGym(name);
            if(gymList.size()>0)
                gym=gymList.get(0);//取第一个
            else
                return DataResult.success(null);
        }


        System.out.print("\n\n\n场馆:"+gymList+"\n");
        String gymName=gym.getGymName();//场馆名称
        String gymId=gym.getGymId();//场馆Id
        String deptId=gym.getDeptId();//重新设置一下deptId

        List<SportAndValue> ageList = new ArrayList<>();
        //上下线人数获取
        List<OnlineNum> onlineNums;
        {
            onlineNums=gymHistoryService.getIsOnlineNum(gymId);
//            System.out.print("asdsad:"+isOnline.toString()+"\n");
        }


        //年龄段数据获取：根据场馆区分
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

        //运动人数获取+所有场馆运动项目占比
        List<SportAndValue> evList=gymHistoryService.getTypeAndValue(gymId);
        List<ExerciseType> exerciseTypes=exerciseTypeService.getTypeByDeptId(deptId);
//        System.out.print("\n\nexerciseTypes："+exerciseTypes+"\n");
        List<String> allGymEx=gymService.getAllTypes();
        List<SportAndValue> allGymSport=new ArrayList<>();
        Map<String, String> exercise=new HashMap<>();
        for(ExerciseType e:exerciseTypes){
            exercise.put(e.getTypeId(),e.getTypeName());
        }
//        System.out.print("\n\n运动人数获取:"+allGymEx+"\n\n");
        {
            Map<String, Integer> map=new HashMap<>();
            Map<String, Integer> map1=new HashMap<>();
            for(SportAndValue s:evList){
                String[] sL=s.getSport().split("-");
                for(String ss:sL)
                    map.put(ss,map.getOrDefault(ss,0)+1);
            }
            for(String as:allGymEx){
                String[] sL=as.split("-");
                for(String ss:sL)
                    map1.put(ss,map1.getOrDefault(ss,0)+1);
            }
            evList=new ArrayList<>();
//            System.out.print("\n\n\n\nmap:"+map+"\n");
//            System.out.print("exercise:"+exercise+"\n");

            for(String key:map.keySet()){
//                System.out.print("keySet:"+key+"\n");
                SportAndValue sportAndValue=new SportAndValue();
//                System.out.print("sport:"+exercise.get(1)+"\n");
                if(exercise.get(key)!=null&&exercise.get(key).length()!=0){
                    sportAndValue.setSport(exercise.get(key));
                    sportAndValue.setValue(map.get(key));
                    evList.add(sportAndValue);
                }
            }
            for(String key2:map1.keySet()){
                SportAndValue sportAndValue=new SportAndValue();
                int k=Integer.parseInt(key2);
                sportAndValue.setSport(exercise.get(k));
                sportAndValue.setValue(map1.get(key2));
                allGymSport.add(sportAndValue);
            }

        }

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
        Date todayT=new Date();
        Date todayS=TimeUtile.getDayStart(todayT);
        Date todayE=TimeUtile.getDayEnd(todayT);
        List<PeopleSportTime> peopleSportTimes=gymHistoryService.getPeopleSportTimes(gymId);//人的运动时长
        List<PeopleSportTime> peopleSportToday=gymHistoryService.getPeopleSportToday(gymId,todayS,todayE);//人的运动时长
        Map<String,Integer> pt=new HashMap<>();
        Map<String,String> idAndName=new HashMap<>();
        {
            int[] pTime=new int[11];//一共11个时间段,8:00-18:00
            for(PeopleSportTime p:peopleSportToday){
//                System.out.print("p:"+p.toString()+"\n");
                String pid=p.getIdcard();
                if(p.getEnd()==null){//没有outtime，默认为starttime+4h
                    p.setEnd(TimeUtile.stepHour(p.getStart(),4));
                }
                String pname=p.getName();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(p.getStart());
                int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
                calendar.setTime(p.getEnd());
                int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
                //今日人数计算
                for(int t=Math.max(8,start);t<=Math.min(18,end);t++){//防止越界添加最大最小
                    pTime[t-8]++;
                }
            }
            for(PeopleSportTime p:peopleSportTimes){
//                System.out.print("p:"+p.toString()+"\n");
                String pid=p.getIdcard();
                if(p.getEnd()==null){//没有outtime，默认为starttime+4h
                    p.setEnd(TimeUtile.stepHour(p.getStart(),4));
                }
                String pname=p.getName();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(p.getStart());
                int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
                calendar.setTime(p.getEnd());
                int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
                //运动时间计算
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
        List<GymOpenTimeVO> gymOpenTimes;
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
//            System.out.print("gymOpenTimes:"+gymOpenTimes.size()+"\n");
            openTime=new int[5][2];//近五天所有场馆的预期开放与实际开放累积存储
            for(int dateT=0;dateT<openTime.length;dateT++){
                openTime[dateT][0]=0;
                openTime[dateT][1]=0;
            }
//            Arrays.fill(openTime,new int[]{0,0});//初始化,这里不能这样,会导致时间段完全一样,因为是用的引用地址
//            System.out.print("近五天的场馆的实际开放时长和预计开放时长\n");
            for(int i=0;i<gymOpenTimes.size();i++){
                String sG=gymOpenTimes.get(i).getGymId();
//                System.out.print("gymOpenTimes:"+i+"  id:"+gymOpenTimes.get(i).getGymId()+"\n");
                //该场馆近五天的闭馆时间:按whichDay时间降序，故一个一个向后判断即可
                gymCloseTimes=gymOpenTimeService.getGymCT(sG,today,fiveDaysAgo);
                int index=0;
                for(int j=0;j<5;j++){//五天
//                    System.out.print("五天:"+j+"\n");
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
                        if(workDay==""||workDay==null||workDay.length()==0||workDay.contains("null"))
                            workDay="9:00-11:30 13:30-17:00";//默认时长
//                        System.out.print("\nworkDay:"+workDay+"\n");
                        h1=workDay.substring(0,workDay.indexOf("-"));
                        h2=workDay.substring(workDay.indexOf("-")+1);
                        workDay=workDay.substring(workDay.indexOf(" ")+1);//获取下半场
                        h3=workDay.substring(0,workDay.indexOf("-"));
                        h4=workDay.substring(workDay.indexOf("-")+1);
                    }else {//周日
                        String weekend=gymOpenTimes.get(i).getSaturday();
//                        System.out.print("weekend:\n\n"+weekend+"\n\n");
                        if(weekend==""||weekend==null||weekend.length()==0||weekend.contains("null")){
                            weekend="9:00-11:30 13:30-19:00";//默认时长
                        }
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
                    if(closeDay==null){
//                        System.out.print("第"+i+"天的第"+j+"个体育馆closeDay缺失");
                    }else {
//                        System.out.print("agoDay  :"+agoDay+"\ncloseDay:"+closeDay+"  "+(agoDay.toString()).equals(closeDay.toString())+"\n\n");
                    }
                    if(closeDay!=null&&(agoDay.toString()).equals(closeDay.toString())){
                        closeTime=gymCloseTimes.get(index).getCloseTime();//获取该日闭馆时间
//                        System.out.print("闭馆时间:"+closeTime+"\n");
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                        index++;//该日期被用掉了
                    }else {//否则用默认
                        differ1=TimeUtile.getHourDiffer(h1,h2,closeTime);
                        differ2=TimeUtile.getHourDiffer(h3,h4,closeTime);
                    }
                    weekDay--;//星期向前走一天
//                    System.out.print("differ3-differ4-differ1-differ2:"+differ3+"-"+differ4+"-"+differ1+"-"+differ2+"\n");
                    openTime[j][0]+=differ3+differ4;//累计预计时间
                    openTime[j][1]+=differ1+differ2;//累计实际时间
                }
            }
            //处理openTime，产出三个数组，预期时长，实际时长，日期数组
            for(int dateT=0;dateT<openTime.length;dateT++){
//                System.out.print("最终openTime:"+openTime[dateT][0]+"-"+openTime[dateT][1]+"\n");
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
            containData.setOnlineNums(onlineNums);
            containData.setAgeList(ageList);
            containData.setEvList(evList);//运动人数占比
            containData.setOrderTimeList(timeList);
            containData.setSportRank(pst2);
            containData.setTodayPeoples(pTimeList);//今日人数
            containData.setLastMonthPeople(gpm);
            containData.setAllGymSport(allGymSport);
            containData.setOpenTime(dates);
            containData.setExceptOpenTime(except);
            containData.setActualOpenTime(actual);
            //加入所有场馆名称
            List<String> gymsName;
            System.out.print("\n\nUdeptId:"+UdeptId+"\n");
            System.out.print("\n\ndeptId:"+deptId+"\n");
            if(UdeptId!=null&&UdeptId.length()>0)
                gymsName=gymService.selectAllNameById(deptId);
            else
                gymsName=gymService.selectAllName();
            containData.setAllGymName(gymsName);
            result.setData(containData);
        }
        System.out.print("containData--------------------\n");
        System.out.print(containData.toString());
        return result;
    }

    @PostMapping("/H5UserSportDate")
    @ApiOperation(value = "H5个人健身数据接口")
    @LogAnnotation(title = "健身数据",action = "获取用户健身数据")
    // @RequiresPermissions("sys:gym:list")
    // interface for h5, testing...
    public DataResult<UserSportData> h5SportDate(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        DataResult<UserSportData> h5Result= DataResult.success();
        String userIdentityCard=customerService.getIDCardByUserId(userId);

        //运动类型占比
        List<ExerciseType> exerciseTypes=exerciseTypeService.getAllTypeWithOutDeleted();
        Map<String, String> exercise=new HashMap<>();
        for(ExerciseType e:exerciseTypes){
            exercise.put(e.getTypeId(),e.getTypeName());
        }
        List<String> sportList=gymHistoryService.getTypeAndValueByIDCard(userIdentityCard);
        List<SportAndValue> userSport=new ArrayList<>();
        Map<String, Integer> map=new HashMap<>();
        for(String as:sportList){
            String[] sL=as.split("-");
            for(String ss:sL)
                map.put(ss,map.getOrDefault(ss,0)+1);
        }
        for(String key:map.keySet()){
            SportAndValue sportAndValue=new SportAndValue();
            int k=Integer.parseInt(key);
            sportAndValue.setSport(exercise.get(k));
            sportAndValue.setValue(map.get(key));
            userSport.add(sportAndValue);
        }

        //运动时长计算：得到以年为键，以每月运动时长为值的map，根据年份即可获得该年份每月运动时长
        List<PeopleSportTime> peopleSportTimes=gymHistoryService.getUserSportTimes(userIdentityCard);//人的运动时长
        Map<Integer,int[]> sportTime=new HashMap<>();//存储每年每月的运动时长
        Date now=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MM");
//        int year=Integer.parseInt(df.format(now));//今年年份
        for(PeopleSportTime peopleSportTime:peopleSportTimes){
            //遍历每个时间点
            Date in=peopleSportTime.getStart();//进入时间
            Date out=peopleSportTime.getEnd();//出去时间

            int year=Integer.parseInt(df.format(in));//年份
            int month=Integer.parseInt(df2.format(in));//月份

            //计算时长
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(in);
            int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
            calendar.setTime(out);
            int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
            int hours=end-start;//运动时长/小时
            int[] mT=new int[11];//一年12个月
            Arrays.fill(mT,0);//填充为0
            int[] MT=sportTime.getOrDefault(year,mT);//该年的月运动时长
            MT[month]+=hours;//累积时长
            sportTime.put(year,MT);//更新该年时长
        }


        return h5Result;
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
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        PageVO<Gym> pageVO=gymService.pageInfo(vo);
        Map<String,Integer> isCloseMap=new HashMap<>();
        List<GymIsClose> gymIsCloses=gymService.getIsClose();
        Map<String,Integer> map=new HashMap<>();
        for(GymIsClose g:gymIsCloses){
            map.put(g.getGymId(),g.getTodayIsClose());
        }
//        System.out.print("gymIsCloses:\n"+gymIsCloses+"\n\n\n\n");
        for(int i=0;i<pageVO.getCurPageSize();i++){
            Gym gym=pageVO.getList().get(i);
            int is=map.getOrDefault(gym.getGymId(),0);//默认值为0
            gym.setToDayIsClose(is);
            pageVO.getList().set(i,gym);//重新赋值
        }
        System.out.print("pageVO:\n"+pageVO+"\n\n\n\n");
        result.setData(pageVO);
        return result;
    }

    @DeleteMapping("/deleteGym")
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
       /* System.out.print("myrequest:"+request.toString()+"\n");
        System.out.print("Constant.ACCESS_TOKEN:\n"+request.getHeader(Constant.ACCESS_TOKEN).toString()+"\n");*/
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
//        System.out.print("新增场地接口:"+userId+"\n");
        vo.setDeptId(userService.getDeptIdFromUserId(userId));
        // NOTICE: the deptId should belong to ...
        System.out.print("新增场地接口："+vo.toString());
        gymService.addGym(vo);
        return DataResult.success();
    }

    @PutMapping("/gym")
    @ApiOperation(value = "更新场地信息接口")
    @LogAnnotation(title = "场馆管理",action = "更新场地信息")
    @RequiresPermissions("sys:gym:update")
    public DataResult updateGymInfo(@RequestBody @Valid GymUpdateReqVO vo, HttpServletRequest request){
       /* System.out.print("myrequest:"+request.toString()+"\n");
        System.out.print("111Constant.ACCESS_TOKEN:\n"+request.getHeader(Constant.ACCESS_TOKEN).toString()+"\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("更新场地信息接口:"+userId+"\n");*/
        //前端时间格式与数据库存储不太一样,做更新
        vo.setWorkDay1(vo.getWorkDay1().replaceAll(" ",""));
        vo.setWorkDay2(vo.getWorkDay2().replaceAll(" ",""));
        vo.setWeekend1(vo.getWeekend1().replaceAll(" ",""));
        vo.setWeekend2(vo.getWeekend2().replaceAll(" ",""));
        System.out.print("更新场地信息接口:"+vo.toString());
        gymService.updateGymInfo(vo);
        return DataResult.success();
    }

    @PostMapping("/nearlyGyms")
    @ApiOperation(value = "附近场馆信息接口")
    @LogAnnotation(title = "附近场馆管理",action = "分页获取场地列表")
    @RequiresPermissions("sys:gym:list")
    public DataResult<List<Gym>> pageInfo(@RequestBody String GPS){
        DataResult<List<Gym>> result=new DataResult<>();

        String[] current=GPS.split(",");//当前经纬度位置

        List<Gym> all=gymService.getAll();
        List<Gym> nearly=new ArrayList<>();
        for(Gym gym:all){
            String[] gps=gym.getGymGps().split(",");
            double lon1=Double.parseDouble(current[0]);
            double lon2=Double.parseDouble(gps[0]);
            double lat1=Double.parseDouble(current[1]);
            double lat2=Double.parseDouble(gps[1]);

            if(CalculateUtil.distance(lat1,lon1,lat2,lon2)<=Constant.nearly_gym)
                nearly.add(gym);
        }
        result.setData(nearly);
        return result;
    }

}
