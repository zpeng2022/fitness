package com.yiie.common.controller;

import com.alibaba.fastjson.JSON;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.BlackUserTypeService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.entity.BlackUserType;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.request.BlackTypeAddReqVO;
import com.yiie.vo.request.BlackTypePageReqVO;
import com.yiie.vo.request.BlackTypeUpdateReqVO;
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

@Api(tags = "黑名单类型管理")
@RequestMapping("/sys")
@RestController
public class BlackTypeController {
    @Autowired
    private UserService userService;

    @Autowired
    BlackUserTypeService blackTypeService;

    @PostMapping("/blackTypes")
    @ApiOperation(value = "分页获取黑名单类型列表接口")
    @LogAnnotation(title = "黑名单类型", action = "分页获取黑名单类型列表")
    @RequiresPermissions("sys:black:list")
    public DataResult<PageVO<BlackUserType>> pageInfo(@RequestBody BlackTypePageReqVO vo, HttpServletRequest request, Model model){
        DataResult<PageVO<BlackUserType>> result= DataResult.success();
//        System.out.print("\n--------------------------------------------\nrequest:"+ JSON.toJSONString(request) +"\n\n");
//        System.out.print("\n--------------------------------------------\nrequest:"+request.getHeader(Constant.ACCESS_TOKEN)+"\n\n");
//        System.out.print("\n--------------------------------------------\nrequest:"+"\n\n");
        String userId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        System.out.print("黑名单类型分页查询："+vo.toString());
        PageVO<BlackUserType> list=blackTypeService.pageInfo(vo);
        System.out.print("list："+list.toString());
        System.out.print("\n\n\n项目地址："+System.getProperty("user.dir")+"\n\n\n");
        result.setData(list);
        return result;
    }


    @DeleteMapping("/blackType")
    @ApiOperation(value = "删除黑名单类型接口")
    @LogAnnotation(title = "黑名单类型管理", action = "删除黑名单类型")
    @RequiresPermissions("sys:black:deleted")
    public DataResult deletedBlackUser(@RequestBody @ApiParam(value = "黑名单类型id集合") List<String> userIds, HttpServletRequest request){
        System.out.print("\n\n删除\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        blackTypeService.deletedBlackUserType(userIds,operationId);
        return DataResult.success();
    }

    @PostMapping("/blackType")
    @ApiOperation(value = "新增黑名单类型接口")
    @LogAnnotation(title = "黑名单类型管理",action = "新增黑名单类型")
    @RequiresPermissions("sys:black:update")
    public DataResult addBlackUser(@RequestBody @Valid BlackTypeAddReqVO vo, HttpServletRequest request){
        System.out.print("\n\n新增\n\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        vo.setTypeId(UUID.randomUUID().toString());//设置一个id
        blackTypeService.addBlackUserType(vo);
        return DataResult.success();
    }

    @PutMapping("/blackType")
    @ApiOperation(value = "更新黑名单类型信息接口")
    @LogAnnotation(title = "黑名单类型管理",action = "更新黑名单类型")
    @RequiresPermissions("sys:black:update")
    public DataResult updateBlackUserInfo(@RequestBody @Valid BlackTypeUpdateReqVO vo, HttpServletRequest request){
        System.out.print("\n\n更新\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("\n\nvo:"+vo);
        blackTypeService.updateBlackUserType(vo,operationId);
        return DataResult.success();
    }
}
