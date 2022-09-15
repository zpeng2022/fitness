package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
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

@Api(tags = "组织模块-场馆管理")
@RequestMapping("/sys")
@RestController
public class gymController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;

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
