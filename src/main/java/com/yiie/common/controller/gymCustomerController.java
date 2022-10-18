package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.*;
import com.yiie.constant.Constant;
import com.yiie.entity.*;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "组织模块-线上用户接口")
@RequestMapping("/sys")
@RestController
public class gymCustomerController {
    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerContactService customerContactService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRecordService customerRecordService;

    @PostMapping("/H5CustomerBasicInfo")
    @ApiOperation(value = "获取用户基本信息接口")
    @LogAnnotation(title = "线上用户接口",action = "分页获取用户基本信息")
    public DataResult<PageVO<Customer>> h5CustomerPageInfo(@RequestBody CustomerPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<Customer>> h5Result= DataResult.success();
        // we need the customer id.
        h5Result.setData(customerService.pageInfo(vo));
        return h5Result;
    }

    @PutMapping("/H5CustomerUpdateBasicInfo")
    @ApiOperation(value = "更新用户基本信息接口")
    @LogAnnotation(title = "线上用户接口",action = "更新用户基本信息")
    public DataResult updateH5BasicInfo(@RequestBody @Valid CustomerUpdateReqVO vo, HttpServletRequest request) throws ParseException {
        customerService.updateCustomer(vo);
        return DataResult.success();
    }


    @PostMapping("/H5CustomerOtherInfo")
    @ApiOperation(value = "获取用户积分信息接口")
    @LogAnnotation(title = "线上用户接口",action = "分页获取用户健身积分信息")
    public DataResult<PageVO<CustomerInfo>> h5CustomerOtherPageInfo(@RequestBody CustomerInfoPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<CustomerInfo>> h5Result= DataResult.success();
        // we need customer_info_id
        h5Result.setData(customerInfoService.pageInfo(vo));
        return h5Result;
    }

    @PutMapping("/H5CustomerUpdateOtherInfo")
    @ApiOperation(value = "更新用户积分信息接口")
    @LogAnnotation(title = "线上用户接口",action = "更新用户健身积分信息")
    public DataResult updateH5OtherInfo(@RequestBody @Valid CustomerInfoUpdateReqVO vo, HttpServletRequest request){
        customerInfoService.updateCustomerInfo(vo);
        return DataResult.success();
    }


    @PostMapping("/H5CustomerContactInfo")
    @ApiOperation(value = "获取用户联系人接口")
    @LogAnnotation(title = "线上用户接口",action = "分页获取用户联系人信息")
    public DataResult<PageVO<CustomerContact>> h5CustomerContactPageInfo(@RequestBody CustomerContactPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<CustomerContact>> h5Result= DataResult.success();
        // customer_contacts_id
        h5Result.setData(customerContactService.pageInfo(vo));
        return h5Result;
    }

    @PostMapping("/H5CustomerRecords")
    @ApiOperation(value = "获取用户记录_健身记录_信用记录_历史运动记录")
    @LogAnnotation(title = "线上用户接口",action = "分页获取用户记录_健身记录_信用记录_历史运动记录")
    public DataResult H5CustomerRecords(@RequestBody CustomerRecordPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<CustomerRecord>> h5Result= DataResult.success();
        int pageNum = vo.getPageNum();
        if(pageNum == 1){
            // step 1.check the time
            // get the finalRecordTime
            // TODO... mapper not implement
            CustomerRecord finalCustomerRecord = customerRecordService.h5CustomerFinalRecord(vo);
            Date finalTime = new Date(0);
            int recordCount = 0;
            if(finalCustomerRecord != null){
                finalTime = finalCustomerRecord.getFinalRecordTime();
                recordCount = finalCustomerRecord.getCustomerRecordCount();
            }
            // if nowTime - finalTime is less than 10 minutes,
            // just send old records. (in step 2)
            // (because the cost to get new records is expensive).
            Date nowTime = new Date();
            long diff = nowTime.getTime() - finalTime.getTime();
            TimeUnit time = TimeUnit.MINUTES;
            long difference = time.convert(diff, TimeUnit.MILLISECONDS);
            /*if(difference > 10){
                // hard part: get new records from gymOrder and gymHistory
                // and store them in our database;
                // TODO... test...
                customerRecordService.h5GetNewCustomerRecord(vo.getCustomerId(), finalTime, ++ recordCount);
            }*/
            customerRecordService.h5GetNewCustomerRecord(vo.getCustomerId(), finalTime, ++ recordCount);
        }
        // step 2. return the record according to the pageNum.
        // TODO... test...
        h5Result.setData(customerRecordService.h5CustomerRecordPageInfo(vo));
        return h5Result;
    }

    @PutMapping("/H5CustomerUpdateContactInfo")
    @ApiOperation(value = "更新用户联系人接口")
    @LogAnnotation(title = "线上用户接口",action = "更新用户联系人信息")
    public DataResult h5CustomerUpdateContactInfo(@RequestBody @Valid CustomerContactUpdateReqVO vo, HttpServletRequest request){
        customerContactService.updateCustomerContact(vo);
        return DataResult.success();
    }

    @DeleteMapping("/H5CustomerDeleteContactInfo")
    @ApiOperation(value = "删除用户联系人接口")
    @LogAnnotation(title = "线上用户接口", action = "删除用户联系人信息")
    public DataResult h5CustomerDeleteContactInfo(@RequestBody @ApiParam(value = "用户联系人id集合") List<String> userIds, HttpServletRequest request){
        customerContactService.deletedCustomerContacts(userIds);
        return DataResult.success();
    }

    @PostMapping("/H5CustomerAddContactInfo")
    @ApiOperation(value = "添加用户联系人接口")
    @LogAnnotation(title = "线上用户接口",action = "新增用户联系人信息")
    public DataResult H5AddGymOrders(@RequestBody @Valid CustomerContactAddReqVO vo, HttpServletRequest request){
        customerContactService.addCustomerContact(vo);
        return DataResult.success();
    }
}
