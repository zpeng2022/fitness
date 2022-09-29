package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.CustomerContactService;
import com.yiie.common.service.CustomerInfoService;
import com.yiie.common.service.CustomerService;
import com.yiie.common.service.GymService;
import com.yiie.constant.Constant;
import com.yiie.entity.Customer;
import com.yiie.entity.CustomerContact;
import com.yiie.entity.CustomerInfo;
import com.yiie.entity.Gym;
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
import java.util.List;

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
    public DataResult updateH5BasicInfo(@RequestBody @Valid CustomerUpdateReqVO vo, HttpServletRequest request){
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
    @RequiresPermissions("sys:gym:update")
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
}
