package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.CustomerService;
import com.yiie.common.service.ExerciseTypeService;
import com.yiie.common.service.GymHistoryService;
import com.yiie.common.service.PhysicalTestService;
import com.yiie.constant.Constant;
import com.yiie.entity.*;
import com.yiie.enums.BaseResponseCode;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.utils.TimeUtile;
import com.yiie.vo.data.GymPath;
import com.yiie.vo.data.PeopleSportTime;
import com.yiie.vo.data.SportAndValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Api(tags = "H5模块-其余接口管理")
@RequestMapping("/H5")
@RestController
public class H5Controller {

    @Autowired
    private GymHistoryService gymHistoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PhysicalTestService physicalTestService;
    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @PostMapping("/getH5Background")
    @ApiOperation(value = "获取主页背景接口")
    @LogAnnotation(title = "获取主页背景",action = "获取主页背景")
    public ResponseEntity<Object> getH5Background(){
        System.out.print("\n获取主页背景接口\n");
        H5Background h5Background=customerService.getBackground();
        String path;
        if(h5Background==null)
            path=Constant.gym_defaultPictureBase64;
        else
            path=h5Background.getBackground();
        GymPath gymPath=new GymPath();
        gymPath.setId(1);
        gymPath.setPath(path);
        List<GymPath> backs=new ArrayList<>();
        backs.add(gymPath);
        return new ResponseEntity<>(backs, HttpStatus.OK);
    }
    @PostMapping("/CustomerBackground")
    @ApiOperation(value = "获取主页背景接口")
    @LogAnnotation(title = "获取主页背景",action = "获取主页背景")
    public ResponseEntity<Object> CustomerBackground(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("\n用户获取主页背景接口\n");
        H5Background h5Background=customerService.getBackgroundByUserId(userId);
        String path;
        if(h5Background==null)
            path=Constant.gym_defaultPictureBase64;
        else
            path=h5Background.getBackground();
      /*  GymPath gymPath=new GymPath();
        gymPath.setId(1);
        gymPath.setPath(path);
        List<GymPath> backs=new ArrayList<>();
        backs.add(gymPath);*/
        return new ResponseEntity<>(path, HttpStatus.OK);
    }


    @PostMapping("/getSportIndex")
    @ApiOperation(value = "获取运动指数接口")
    @LogAnnotation(title = "获取运动指数",action = "获取运动指数")
    public DataResult getSportIndex(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(userId==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
        int index=0;
        //计算指数...

        return DataResult.success(index);
    }
    @PostMapping("/getTodaySportData")
    @ApiOperation(value = "获取今日运动数据接口")
    @LogAnnotation(title = "获取今日运动数据",action = "获取今日运动数据")
    public DataResult getTodaySportData(HttpServletRequest request) throws ParseException {
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(userId==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
        Customer customer=customerService.getCustomerById(userId);
        int index=0;
        String customerIdentityCard=customer.getCustomer_identity_card();//获取身份证
        //计算数据
        //健身天数：gym_history 用记录分析
        int sportDays=gymHistoryService.getCustomerSportDay(customerIdentityCard);
        //使用天数：customer 从注册开始计算
        Date register=customer.getCustomer_create_time();//注册时间
        int useDays= TimeUtile.getTimeDiffer_day(register);
        //去过场所：gym_history 场馆id计数
        int gyms=gymHistoryService.getCustomerGymNum(customerIdentityCard);
        //以什么形式返回....

        return DataResult.success();
    }

    @PostMapping("/getSportData")
    @ApiOperation(value = "获取健身数据接口")
    @LogAnnotation(title = "获取健身数据",action = "获取健身数据")
        public DataResult getSportData(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(userId==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
        Customer customer=customerService.getCustomerById(userId);
        //今日步数：无法计算

        //运动总时长：参考数智，多加个customerId
        int totalTime=0;
        //运动时长分布：参考数智，多加个customerId以及按月份计数
        int[] months=new int[13];//每个月运动时长

        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);//开始年份
        //人的运动时长
        List<PeopleSportTime> peopleSportTimes=gymHistoryService.getPeopleSportTimesByCustomerId(customer.getCustomer_identity_card(),year);
        //运动分布计算
        List<PeopleSportTime> peopleSportTimes2=gymHistoryService.getPeopleSportTimesByCustomerId2(customer.getCustomer_identity_card());
        for(PeopleSportTime p:peopleSportTimes){
            if(p.getEnd()==null){//没有outtime，默认为starttime+4h
                p.setEnd(TimeUtile.stepHour(p.getStart(),4));
            }
            calendar.setTime(p.getStart());
            int month=calendar.get(Calendar.MONTH);//月份
            int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
            calendar.setTime(p.getEnd());
            int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
            int hour=end-start;
            months[month]+=hour;//每个月运动时长计算
        }
        List<SportAndValue> evList=new ArrayList<>();
        List<ExerciseType> exerciseTypes=exerciseTypeService.getAllType();
        Map<String, String> exercise=new HashMap<>();
        for(ExerciseType e:exerciseTypes){
            exercise.put(e.getTypeId(),e.getTypeName());
        }
        Map<String, Integer> map=new HashMap<>();
        for(PeopleSportTime p:peopleSportTimes2){
            calendar.setTime(p.getStart());
            int month=calendar.get(Calendar.MONTH);//月份
            int start=calendar.get(Calendar.HOUR_OF_DAY);//开始小时
            calendar.setTime(p.getEnd());
            int end=calendar.get(Calendar.HOUR_OF_DAY);//结束小时
            int hour=end-start;
            totalTime+=hour;//总时长
            //运动类型
            String type=p.getExerciseType();
            String[] sL=type.split("-");
            for(String ss:sL)
                map.put(ss,map.getOrDefault(ss,0)+1);
        }
        for(String key:map.keySet()){
            SportAndValue sportAndValue=new SportAndValue();
            if(exercise.get(key)!=null&&exercise.get(key).length()!=0){
                sportAndValue.setSport(exercise.get(key));
                sportAndValue.setValue(map.get(key));
                evList.add(sportAndValue);
            }
        }
        //总消耗卡路里：无法计算

        //累积运动天数：等同于健身天数？

        //运动项目分布：参考数智，多加个customerId

        return DataResult.success();
    }

    @PostMapping("/getSportTestData")
    @ApiOperation(value = "获取体测报告数据接口")
    @LogAnnotation(title = "获取体测报告数据",action = "获取体测报告数据")
    public DataResult getSportTestData(HttpServletRequest request) {
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(userId==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
        Customer customer=customerService.getCustomerById(userId);
        PhysicalTestData physicalTestData=physicalTestService.getByCustomerId(userId);//暂时认为学籍号就是customerId，mapper还未完善
        return DataResult.success();
    }
}
