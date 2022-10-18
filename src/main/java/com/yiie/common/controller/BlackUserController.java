package com.yiie.common.controller;

import com.alibaba.excel.EasyExcel;
import com.google.gson.Gson;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.BlackUserService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.utils.*;
import com.yiie.vo.request.*;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Api(tags = "组织模块-禁入名单管理")
@RequestMapping("/sys")
@RestController
public class BlackUserController {

    @Autowired
    private BlackUserService blackUserService;

    @Autowired
    private UserService userService;

    @PostMapping("/uploadBlackFile")
    @ApiOperation(value = "导入黑名单人员列表接口")
    @LogAnnotation(title = "黑名单",action = "黑名单")
    @RequiresPermissions("sys:blackusers:add")
    public String loadBlackList(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.print("上传黑名单\n");
        if (file.isEmpty()) {
            System.out.print( "文件为空");
            throw new IllegalArgumentException("未上传文件");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);

        // 文件上传后的路径
        String blackListPath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\blackList\\";
        File dest = new File(blackListPath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

            System.out.print( "保存文件成功\n");
//            ssc loadBlackList=new ssc();
//            loadBlackList.readXSL(blackListPath+fileName);
            EasyExcel.read(blackListPath+fileName, ExcelBlackInfoVO.class,new ExcelListener()).sheet().doRead();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("加载入数据库\n");
        Gson gson = new Gson();
        String json = gson.toJson(ResultEntity.successWithoutData());

        return json;
    }

    @PostMapping("/blackusers")
    @ApiOperation(value = "分页获取禁入人员列表接口")
    @LogAnnotation(title = "禁入名单管理", action = "分页获取禁入人员列表")
    @RequiresPermissions("sys:blackusers:list")
    public DataResult<PageVO<BlackUser>> pageInfo(@RequestBody BlackUserPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<BlackUser>> result= DataResult.success();
        // we need to set the deptID
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // get deptId, database will get information that belongs to this deptID.
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptID(deptID);
        result.setData(blackUserService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/blackuser")
    @ApiOperation(value = "删除禁入人员接口")
    @LogAnnotation(title = "禁入名单管理", action = "删除禁入人员")
    @RequiresPermissions("sys:blackusers:deleted")
    public DataResult deletedBlackUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        blackUserService.deletedBlackUsers(userIds,operationId);
        return DataResult.success();
    }

    @PostMapping("/blackuser")
    @ApiOperation(value = "新增禁入人员接口")
    @LogAnnotation(title = "禁入名单管理",action = "新增用户")
    @RequiresPermissions("sys:blackusers:add")
    public DataResult addBlackUser(@RequestBody @Valid BlackUserAddReqVo vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // get deptId, database will get information that belongs to this deptID.
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptID(deptID);
        blackUserService.addBlackUser(vo);
        return DataResult.success();
    }

    @PutMapping("/blackuser")
    @ApiOperation(value = "更新禁入人员信息接口")
    @LogAnnotation(title = "禁入名单管理",action = "更新用户信息")
    @RequiresPermissions("sys:blackusers:update")
    public DataResult updateBlackUserInfo(@RequestBody @Valid BlackUserUpdateReqVO vo, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptID(deptID);
        blackUserService.updateBlackUserInfo(vo,operationId);
        return DataResult.success();
    }

}
