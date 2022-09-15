package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.mapper.GymHistoryMapper;
import com.yiie.common.service.GymCommentService;
import com.yiie.common.service.GymHistoryService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.GymComments;
import com.yiie.entity.GymHistory;
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

@Api(tags = "组织模块-进出历史管理")
@RequestMapping("/sys")
@RestController
public class gymHistoryController {
    @Autowired
    private GymHistoryService gymHistoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/gymHistories")
    @ApiOperation(value = "历史记录接口")
    @LogAnnotation(title = "历史记录管理",action = "分页获取历史记录列表")
    @RequiresPermissions("sys:gymHistory:list")
    public DataResult<PageVO<GymHistory>> pageInfo(@RequestBody GymHistoryPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymHistory>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        result.setData(gymHistoryService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymHistory")
    @ApiOperation(value = "删除历史记录接口")
    @LogAnnotation(title = "历史记录管理", action = "删除历史记录")
    @RequiresPermissions("sys:gymHistory:deleted")
    public DataResult deletedGymHistories(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymHistoryService.deletedGymHistories(userIds);
        return DataResult.success();
    }

    @PostMapping("/gymHistory")
    @ApiOperation(value = "新增历史记录接口")
    @LogAnnotation(title = "历史记录管理",action = "新增历史记录信息")
    @RequiresPermissions("sys:gymHistory:add")
    public DataResult addGymHistories(@RequestBody @Valid GymHistoryAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // NOTICE: the deptId should belong to ...
        gymHistoryService.addGymHistories(vo);
        return DataResult.success();
    }

    @PutMapping("/gymHistory")
    @ApiOperation(value = "更新历史记录接口")
    @LogAnnotation(title = "历史记录管理",action = "更新历史记录信息")
    @RequiresPermissions("sys:gymHistory:update")
    public DataResult updateGymHistoriesInfo(@RequestBody @Valid GymHistoryUpdateReqVO vo, HttpServletRequest request){
        gymHistoryService.updateGymHistoriesInfo(vo);
        return DataResult.success();
    }

}
