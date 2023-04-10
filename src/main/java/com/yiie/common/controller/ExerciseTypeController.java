package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.ExerciseTypeService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.entity.ExerciseType;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Api(tags = "运动类型管理")
@RequestMapping("/sys")
@RestController
public class ExerciseTypeController {
    @Autowired
    private UserService userService;

    @Autowired
    ExerciseTypeService exerciseTypeService;

    @PostMapping("/exerciseTypes")
    @ApiOperation(value = "分页获取运动类型列表接口")
    @LogAnnotation(title = "运动类型", action = "分页获取运动类型列表")
    @RequiresPermissions("sys:exercise:list")
    public DataResult<PageVO<ExerciseType>> pageInfo(@RequestBody ExerciseTypePageReqVO vo, HttpServletRequest request, Model model){
        DataResult<PageVO<ExerciseType>> result= DataResult.success();
        String userId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        System.out.print("运动类型分页查询："+vo.toString());
        PageVO<ExerciseType> list=exerciseTypeService.pageInfo(vo);
        System.out.print("list："+list.toString());
        result.setData(list);
        return result;
    }


    @DeleteMapping("/exerciseType")
    @ApiOperation(value = "删除运动类型接口")
    @LogAnnotation(title = "运动类型管理", action = "删除运动类型")
    @RequiresPermissions("sys:exercise:deleted")
    public DataResult deletedBlackUser(@RequestBody @ApiParam(value = "运动类型id集合") List<String> userIds, HttpServletRequest request){
        System.out.print("\n\n删除\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        exerciseTypeService.deletedExerciseType(userIds,operationId);
        return DataResult.success();
    }

    @PostMapping("/exerciseType")
    @ApiOperation(value = "新增运动类型接口")
    @LogAnnotation(title = "运动类型管理",action = "新增运动类型")
    @RequiresPermissions("sys:exercise:update")
    public DataResult addBlackUser(@RequestBody @Valid ExerciseTypeAddReqVO vo, HttpServletRequest request){
        System.out.print("\n\n新增\n\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        if(deptId!=null)
            vo.setDeptId(deptId);
        vo.setTypeId(UUID.randomUUID().toString());
        exerciseTypeService.addExerciseType(vo);
        return DataResult.success();
    }

    @PutMapping("/exerciseType")
    @ApiOperation(value = "更新运动类型信息接口")
    @LogAnnotation(title = "运动类型管理",action = "更新运动类型")
    @RequiresPermissions("sys:exercise:update")
    public DataResult updateBlackUserInfo(@RequestBody @Valid ExerciseTypeUpdateReqVO vo, HttpServletRequest request){
        System.out.print("\n\n更新\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("\n\nvo:"+vo);
        exerciseTypeService.updateExerciseType(vo,operationId);
        return DataResult.success();
    }


}
