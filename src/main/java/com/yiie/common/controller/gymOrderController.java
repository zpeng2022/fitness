package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymOrderService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.GymHistory;
import com.yiie.entity.GymOrder;
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

@Api(tags = "组织模块-预约管理")
@RequestMapping("/sys")
@RestController
public class gymOrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private GymOrderService gymOrderService;

    @PostMapping("/H5GymOrders")
    @ApiOperation(value = "H5预约记录接口")
    @LogAnnotation(title = "预约管理",action = "分页获取预约信息列表")
    public DataResult<PageVO<GymOrder>> H5PageInfo(@RequestBody GymOrderPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymOrder>> result= DataResult.success();
        // vo.setDeptId(deptID);
        result.setData(gymOrderService.h5PageInfo(vo));
        return result;
    }

    @PostMapping("/gymOrders")
    @ApiOperation(value = "预约记录接口")
    @LogAnnotation(title = "预约管理",action = "分页获取预约信息列表")
    @RequiresPermissions("sys:gymOrder:list")
    public DataResult<PageVO<GymOrder>> pageInfo(@RequestBody GymOrderPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymOrder>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        result.setData(gymOrderService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymOrder")
    @ApiOperation(value = "删除预约记录接口")
    @LogAnnotation(title = "预约管理", action = "删除预约信息列表")
    @RequiresPermissions("sys:gymOrder:deleted")
    public DataResult deletedGymOrders(@RequestBody @ApiParam(value = "用户id集合") List<String> orderIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymOrderService.deletedGymOrders(orderIds);
        return DataResult.success();
    }

    @PostMapping("/gymOrder")
    @ApiOperation(value = "新增预约记录接口")
    @LogAnnotation(title = "预约管理",action = "新增预约信息列表")
    @RequiresPermissions("sys:gymOrder:add")
    public DataResult addGymOrders(@RequestBody @Valid GymOrderAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymOrderService.addGymOrders(vo);
        return DataResult.success();
    }

    @PutMapping("/gymOrder")
    @ApiOperation(value = "更新预约记录接口")
    @LogAnnotation(title = "预约管理",action = "更新预约信息列表")
    @RequiresPermissions("sys:gymOrder:update")
    public DataResult updateGymOrdersInfo(@RequestBody @Valid GymOrderUpdateReqVO vo, HttpServletRequest request){
        gymOrderService.updateGymOrdersInfo(vo);
        return DataResult.success();
    }
}
