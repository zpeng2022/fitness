package com.yiie.common.controller;

import com.alibaba.excel.EasyExcel;
import com.google.gson.Gson;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.PhysicalTestService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.entity.BlackUserType;
import com.yiie.entity.PhysicalTestData;
import com.yiie.utils.*;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.data.ExcelBlackType;
import com.yiie.vo.data.PhysicalInfoVo;
import com.yiie.vo.request.BlackUserAddReqVo;
import com.yiie.vo.request.BlackUserPageReqVO;
import com.yiie.vo.request.BlackUserUpdateReqVO;
import com.yiie.vo.request.PhysicalTestReqVO;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Api(tags = "H5模块-体测管理")
@RequestMapping("/physical")
@RestController
public class PhysicalController {
    @Autowired
    private UserService userService;

    @Autowired
    private PhysicalTestService physicalTestService;

    @PostMapping("/uploadPhysicalTestFile")
    @ApiOperation(value = "导入体测数据列表接口")
    @LogAnnotation(title = "体测数据",action = "体测数据")
    @RequiresPermissions("sys:physical:upload")
    public String loadPhysicalList(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.print("上传黑名单\n");
        if (file.isEmpty()) {
            System.out.print( "文件为空");
            throw new IllegalArgumentException("未上传文件");
        }
        File newF=null;
        try {
            //将MultipartFile转为File
            newF = FileUtil.multipartFileToFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("上传失败，请检查文件格式再次尝试");
        } finally {
            System.out.print("\n\n加载入数据库..............\n");
            ExcelListenerPhysicalTest excelListenerPhysicalTest= new ExcelListenerPhysicalTest();
            EasyExcel.read(newF, PhysicalInfoVo.class,excelListenerPhysicalTest).sheet().doRead();
            FileUtil.deleteTempFile(newF);
        }
        System.out.print("\n\n加载成功..............\n");
        Gson gson = new Gson();
        String json = gson.toJson(ResultEntity.successWithoutData());

        return json;
    }


    @PostMapping("/physicals")
    @ApiOperation(value = "分页获取体测数据列表接口")
    @LogAnnotation(title = "体测数据管理", action = "分页获取体测数据列表")
    @RequiresPermissions("sys:physical:list")
    public DataResult<PageVO<PhysicalTestData>> pageInfo(@RequestBody PhysicalTestReqVO vo, HttpServletRequest request){
        DataResult<PageVO<PhysicalTestData>> result=new DataResult<>();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
//        String deptId= userService.getDeptIdFromUserId(userId);
        System.out.print("\n\n查询："+vo.toString()+"\n\n");
        PageVO<PhysicalTestData> list=physicalTestService.pageInfo(vo);
        System.out.print("\n\n体测数据分页查询："+list+"\n\n");
        result.setData(list);
        return result;
    }

    @DeleteMapping("/physical")
    @ApiOperation(value = "删除体测数据接口")
    @LogAnnotation(title = "体测数据管理", action = "删除体测数据")
    @RequiresPermissions("sys:physical:deleted")
    public DataResult deletedBlackUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("\n删除体测数据接口\n");
        System.out.print(userIds);
        physicalTestService.deletedPhysical(userIds,operationId);
        return DataResult.success();
    }

    @PostMapping("/physical")
    @ApiOperation(value = "新增体测数据接口")
    @LogAnnotation(title = "体测数据管理",action = "新增体测数据")
    @RequiresPermissions("sys:physical:add")
    public DataResult addPhysical(@RequestBody @Valid PhysicalTestData vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // get deptId, database will get information that belongs to this deptID.
        String deptID = userService.getDeptIdFromUserId(userId);
        String id=UUID.randomUUID().toString();
        vo.setId(id);
        vo.setCreateTime(new Date());
        vo.setDeleted(1);
        System.out.print("\n\n新增体测数据:"+vo);
        physicalTestService.addPhysical(vo);
        return DataResult.success();
    }

    @PutMapping("/physical")
    @ApiOperation(value = "更新体测数据接口")
    @LogAnnotation(title = "体测数据管理",action = "更新体测数据")
    @RequiresPermissions("sys:physical:update")
    public DataResult updateBlackUserInfo(@RequestBody @Valid PhysicalTestData vo, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
//        String deptID = userService.getDeptIdFromUserId(userId);
        physicalTestService.updatePhysical(vo);
        return DataResult.success();
    }

}
