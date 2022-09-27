package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymCommentService;
import com.yiie.common.service.GymCommentTagService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.entity.GymComments;
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

@Api(tags = "组织模块-评价管理")
@RequestMapping("/sys")
@RestController
public class gymCommentController {
    @Autowired
    private GymCommentService gymCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private GymCommentTagService gymCommentTagService;

    // /sys/H5GymComments
    @PostMapping("/H5GymComments")
    @ApiOperation(value = "H5评论信息接口")
    @LogAnnotation(title = "评论管理",action = "分页获取评论信息列表")
    public DataResult<PageVO<GymComments>> h5PageInfo(@RequestBody GymCommentPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymComments>> result= DataResult.success();
        result.setData(gymCommentService.h5PageInfo(vo));
        return result;
    }

    @PostMapping("/gymComments")
    @ApiOperation(value = "评论信息接口")
    @LogAnnotation(title = "评论管理",action = "分页获取评论信息列表")
    @RequiresPermissions("sys:gymComment:list")
    public DataResult<PageVO<GymComments>> pageInfo(@RequestBody GymCommentPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymComments>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        result.setData(gymCommentService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymComment")
    @ApiOperation(value = "删除评论信息接口")
    @LogAnnotation(title = "评论管理", action = "删除评论信息")
    @RequiresPermissions("sys:gymComment:deleted")
    public DataResult deletedGymComments(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymCommentService.deletedGymComments(userIds);
        return DataResult.success();
    }

    @PostMapping("/gymComment")
    @ApiOperation(value = "新增评论信息接口")
    @LogAnnotation(title = "评论管理",action = "新增评论信息")
    @RequiresPermissions("sys:gymComment:add")
    public DataResult addGymComments(@RequestBody @Valid GymCommentAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // NOTICE: the deptId should belong to ...
        gymCommentService.addGymComments(vo);
        return DataResult.success();
    }

    @PutMapping("/gymComment")
    @ApiOperation(value = "更新评论信息接口")
    @LogAnnotation(title = "评论管理",action = "更新评论信息")
    @RequiresPermissions("sys:gymComment:update")
    public DataResult updateGymCommentsInfo(@RequestBody @Valid GymCommentUpdateReqVO vo, HttpServletRequest request){
        gymCommentService.updateGymCommentsInfo(vo);
        return DataResult.success();
    }

}
