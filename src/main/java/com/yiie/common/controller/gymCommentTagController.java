package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymCommentTagService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.entity.GymCustomTags;
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

@Api(tags = "组织模块-评价标签管理")
@RequestMapping("/sys")
@RestController
public class gymCommentTagController {
    @Autowired
    private UserService userService;

    @Autowired
    private GymCommentTagService gymCommentTagService;

    @PostMapping("/gymCommentTags")
    @ApiOperation(value = "评价标签信息接口")
    @LogAnnotation(title = "评价标签管理",action = "分页获取评价标签信息")
    @RequiresPermissions("sys:gymCommentTag:list")
    public DataResult<PageVO<GymCustomTags>> pageInfo(@RequestBody GymCommentTagPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymCustomTags>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        result.setData(gymCommentTagService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymCommentTag")
    @ApiOperation(value = "删除评价标签信息接口")
    @LogAnnotation(title = "评价标签信息管理", action = "删除评价标签信息")
    @RequiresPermissions("sys:gymCommentTag:deleted")
    public DataResult deletedGymCustomTags(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymCommentTagService.deletedGymCustomTags(userIds);
        return DataResult.success();
    }

    @PostMapping("/gymCommentTag")
    @ApiOperation(value = "新增评价标签信息接口")
    @LogAnnotation(title = "评价标签信息管理",action = "新增评价标签信息")
    @RequiresPermissions("sys:gymCommentTag:add")
    public DataResult addGymCustomTags(@RequestBody @Valid GymCommentTagAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // NOTICE: the deptId should belong to ...
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        gymCommentTagService.addGymCustomTags(vo);
        return DataResult.success();
    }

    @PutMapping("/gymCommentTag")
    @ApiOperation(value = "更新评价标签信息接口")
    @LogAnnotation(title = "场馆管理",action = "更新场地信息")
    @RequiresPermissions("sys:gymCommentTag:update")
    public DataResult updateGymCustomTagsInfo(@RequestBody @Valid GymCommentTagUpdateReqVO vo, HttpServletRequest request){
        gymCommentTagService.updateGymCustomTagsInfo(vo);
        return DataResult.success();
    }
}