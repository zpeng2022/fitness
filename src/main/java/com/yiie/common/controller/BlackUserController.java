package com.yiie.common.controller;

import com.alibaba.excel.EasyExcel;
import com.google.gson.Gson;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.BlackUserService;
import com.yiie.common.service.BlackUserTypeService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.entity.BlackUserType;
import com.yiie.utils.*;
import com.yiie.vo.data.ExcelBlackType;
import com.yiie.vo.request.*;
import com.yiie.vo.data.ExcelBlackInfoVO;
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
import javax.xml.crypto.Data;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Api(tags = "组织模块-禁入名单管理")
@RequestMapping("/sys")
@RestController
public class BlackUserController {

    @Autowired
    private BlackUserService blackUserService;

    @Autowired
    private BlackUserTypeService blackUserTypeService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping(value="/download", produces = {"application/text;charset=UTF-8"})
    public String down(HttpServletResponse response, Long id) throws UnsupportedEncodingException {//成功
        String filePath = "C:\\xl\\黑名单模板.xls";
//        String filePath = "\\var\\bgman\\upload\\黑名单模板.xls";
        response.setContentType("application/x-download");
        String fileName = URLEncoder.encode("黑名单模板.xls", "UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        File file = new File(filePath);
        try {
            InputStream stream = new FileInputStream(file);

            ServletOutputStream out = response.getOutputStream();

            byte buff[] = new byte[1024];
            int length = 0;

            while ((length = stream.read(buff)) > 0) {
                out.write(buff,0,length);
            }
            stream.close();

            out.close();

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return "下载失败";
        }

        return "下载成功";
    }
   /* @ResponseBody
    @GetMapping(value="/download1", produces = {"application/text;charset=UTF-8"})
    public String down1(HttpServletResponse response, Long id){//失败
        try {
            //根据文件id查询文件路径
            String filePath = Constant.fileRoot+"blackModel\\黑名单模板.xls";
            //根据文件路径下载文件信息
            FileUtil.down(response, filePath);
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            return "下载成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "下载失败";
    }*/

    @GetMapping("/downloadModel")
    @ApiOperation(value = "下载模板型接口")
    public DataResult getModel(HttpServletRequest request) throws UnsupportedEncodingException {
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId=userService.getDeptIdFromUserId(userId);
        BlackUserType blackUserType=new BlackUserType();
        blackUserType.setDeptId(deptId);
        List<BlackUserType> type=blackUserTypeService.getByDeptId(blackUserType);
        List<ExcelBlackType> userData = new ArrayList<>();
        for(int i=0;i<type.size();i++){
            ExcelBlackType data = new ExcelBlackType();
            data.setTypeId(type.get(i).getTypeId());
            data.setTypeName(type.get(i).getTypeName());
            userData.add(data);
        }
        System.out.print("\n\n类型:"+userData+"\n");
        // 指定写的路径
        String filePath = "C:\\xl\\黑名单模板.xls";
//        String filePath = "D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\blackModel\\黑名单模板.xls";
//        String filePath = "/var/bgman/upload/黑名单模板.xls";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，
        // 名字为模板 然后文件流会自动关闭
        EasyExcel.write(filePath, ExcelBlackType.class)
                .sheet("模板")
                .doWrite(userData);


        //根据文件id查询文件路径
//        String filePath = Constant.fileRoot+"blackModel\\黑名单模板.xls";

        /*response.setContentType("application/x-download");
        String fileName = URLEncoder.encode("黑名单模板.xls", "UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        File file = new File(filePath);
        try {
            InputStream stream = new FileInputStream(file);

            ServletOutputStream out = response.getOutputStream();

            byte buff[] = new byte[1024];
            int length = 0;

            while ((length = stream.read(buff)) > 0) {
                out.write(buff,0,length);
            }
            stream.close();

            out.close();

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return DataResult.success("下载失败");
        }
*/
        return DataResult.success("下载成功");

    }

    @PostMapping("/getBUType")
    @ApiOperation(value = "黑名单人员类型接口")
    @RequiresPermissions("sys:black:list")
    public DataResult getBUType(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId=userService.getDeptIdFromUserId(userId);
        System.out.print("\n\n\n获取黑名单类型："+deptId+"\n\n\n");
        BlackUserType blackUserType=new BlackUserType();
        blackUserType.setDeptId(deptId);
        List<BlackUserType> type=blackUserTypeService.getByDeptId(blackUserType);
        return DataResult.success(type);
    }

    @PostMapping("/getDeptId")
    @ApiOperation(value = "黑名单人员类型接口")
    public DataResult getDeptId(HttpServletRequest request){
        String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId=userService.getDeptIdFromUserId(userId);
        return DataResult.success(deptId);
    }

    @PostMapping("/uploadBlackFile")
    @ApiOperation(value = "导入黑名单人员列表接口")
    @LogAnnotation(title = "黑名单",action = "黑名单")
//    @RequiresPermissions("sys:blackusers:upload")
    public String loadBlackList(@RequestParam("file") MultipartFile file,HttpServletRequest request,@RequestParam("deptId") String deptId) throws IOException {
       /* String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("userId:"+userId+"\n");*/
//        String deptId=userService.getDeptIdFromUserId(userId);
//      String deptId="72a4f388-50f8-4019-8c67-530cd7c74e7a";
        if (deptId==null) {
            System.out.print( "查询不到deptId");
            throw new IllegalArgumentException("查询不到deptId");
        }
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
            ExcelListener excelListener= new ExcelListener();
            excelListener.setDeptId(deptId);
            EasyExcel.read(newF, ExcelBlackInfoVO.class,excelListener).sheet().doRead();
            FileUtil.deleteTempFile(newF);
        }
        System.out.print("\n\n加载成功..............\n");
        Gson gson = new Gson();
        String json = gson.toJson(ResultEntity.successWithoutData());

        return json;
    }
/*
    @PostMapping("/uploadBlackFile")
    @ApiOperation(value = "导入黑名单人员列表接口")
    @LogAnnotation(title = "黑名单",action = "黑名单")
    @RequiresPermissions("sys:blackusers:upload")
    public String loadBlackList(@RequestParam("file") MultipartFile file,HttpServletRequest request,@RequestParam("deptId") String deptId) throws IOException {
       */
/* String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("userId:"+userId+"\n");*//*

//        String deptId=userService.getDeptIdFromUserId(userId);
//        String deptId="72a4f388-50f8-4019-8c67-530cd7c74e7a";
        if (deptId==null) {
            System.out.print( "查询不到deptId");
            throw new IllegalArgumentException("查询不到deptId");
        }
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
//        String blackListPath=Constant.fileRoot+"blackList\\";
        String blackListPath="/var/bgman/upload/";
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd/");
        String datePath= sdf.format(new Date());
//        System.out.print("\n\n\n\n当前日期:"+blackListPath + fileName+"\n");
//        System.out.print("当前日期2:"+blackListPath+datePath+fileName+"\n");
//        String Path=blackListPath+datePath+fileName;
        String Path=blackListPath+fileName;
//        Path.replaceAll("/","\\\\");//注意转义
        System.out.println("\n\n黑名单路径：" + Path+"\n\n\n");
        File dest = new File(Path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

            System.out.print( "保存文件成功\n");
//            ssc loadBlackList=new ssc();
//            loadBlackList.readXSL(blackListPath+fileName);
            ExcelListener excelListener= new ExcelListener();
            excelListener.setDeptId(deptId);
//            EasyExcel.read(Path, ExcelBlackInfoVO.class,excelListener).sheet().doRead();
            EasyExcel.read((File) file, ExcelBlackInfoVO.class,excelListener).sheet().doRead();
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
*/

    @PostMapping("/blackusers")
    @ApiOperation(value = "分页获取禁入人员列表接口")
    @LogAnnotation(title = "禁入名单管理", action = "分页获取禁入人员列表")
    @RequiresPermissions("sys:blackusers:list")
    public DataResult<PageVO<BlackUser>> pageInfo(@RequestBody BlackUserPageReqVO vo, HttpServletRequest request, Model model){
        DataResult<PageVO<BlackUser>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
//        System.out.print("\n\ndeptId："+deptId+"\n\n");
//        System.out.print("\n\nlength："+deptId.length()+"\n\n");
//        System.out.print("\n\n黑名单分页查询："+vo.toString()+"\n\n");
        if(deptId!=null&&deptId.length()!=0&&deptId!=""){
//            System.out.print("\n\n设置deptId\n\n");
            vo.setDeptId(deptId);
            model.addAttribute("deptId",deptId);
        }
        System.out.print("\n\n黑名单分页查询："+vo.toString()+"\n\n");
        PageVO<BlackUser> list=blackUserService.pageInfo(vo);
        System.out.print("\n\n\n\n\nlist："+list.toString());

        result.setData(list);
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
