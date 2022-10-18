package com.yiie.common.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymService;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.enums.BaseResponseCode;
import com.yiie.utils.DataResult;
import com.yiie.utils.OSS;
import com.yiie.vo.request.GymUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Time：2019-12-30 23:41
 * Email： yiie315@163.com
 * Desc：视图索引控制器 ，返回视图
 *
 * @author： yiie
 * @version：1.0.0
 */
@Api(tags = "视图",description = "负责返回视图")
@Controller
@RequestMapping("/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private GymService gymService;


   /* @PostMapping("/loadPicture")
    @ApiOperation(value = "场地图片接口")
    @LogAnnotation(title = "场馆图片",action = "上传图片")
    public String uploadPhoto(@RequestParam("picture") MultipartFile file, @RequestParam("gymId") String gymId,Model model){
        System.out.print("进入上传\n");
        System.out.print("上传文件："+file+"\n");
        System.out.print("上传id："+gymId+"\n");
        model.addAttribute("gymId",gymId);
        if (file.isEmpty()) {
            System.out.print( "文件为空");
//            throw new IllegalArgumentException("未上传文件");
            model.addAttribute("loadPicture",3);
            return "gym/gym_list";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);

        //获取Id
        String fileId=fileName.substring(0,fileName.indexOf('.')).replaceAll("_","");
        System.out.println("gymId：" + gymId);
        Gym gym=gymService.getById(gymId);
        if(gym!=null){
            System.out.print("该gym信息存在:"+gym);
        }else {
            System.out.print("该gym信息不存在");
            model.addAttribute("loadPicture",2);
            return "gym/gym_list";
        }

        // 文件上传后的路径
        String picturePath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\gymPicture\\";
        File dest = new File(picturePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            System.out.print( "图片保存成功");
            GymUpdateReqVO gymUpdateReqVO=new GymUpdateReqVO();
            BeanUtils.copyProperties(gym,gymUpdateReqVO);
            gymUpdateReqVO.setGymPicturesPath(picturePath + fileName);
            gymService.updateGymInfo(gymUpdateReqVO);
            System.out.print("图片路径加载入数据库");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("loadPicture",1);
        return "gym/gym_list";
    }
    @PostMapping("/loadPicture2")
    @ApiOperation(value = "场地图片接口")
    @LogAnnotation(title = "场馆图片",action = "上传图片")
    public String uploadPhoto2(@RequestParam("picture") MultipartFile[] files, @RequestParam("gymId") String gymId,Model model){
        System.out.print("进入上传\n");
        Gym gym=gymService.getById(gymId);
        String path=gym.getGymPicturesPath();
        int pictureNum=0;
        for(char pc:path.toCharArray()){
            if(pc==';')
                pictureNum++;
        }
        System.out.print("当前存在图片数："+pictureNum);
        if(gym!=null){
            System.out.print(gymId+" :该gym信息存在: "+gym);
        }else {
            System.out.print("该gym信息不存在");
            model.addAttribute("loadPicture",2);
            return "gym/gym_list";
        }

        for(MultipartFile file:files){
            System.out.print("上传文件："+file+"\n");
            model.addAttribute("gymId",gymId);
            if (file.isEmpty()) {
                System.out.print( "文件为空");
//            throw new IllegalArgumentException("未上传文件");
                model.addAttribute("loadPicture",3);
                return "gym/gym_list";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传的后缀名为：" + suffixName);

            //获取Id
//            String fileId=fileName.substring(0,fileName.indexOf('.')).replaceAll("_","");

            // 文件上传后的路径
            String picturePath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\gymPicture\\";
            File dest = new File(picturePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                System.out.print( "图片保存成功\n");
                if(pictureNum==3){
                    System.out.print( "已满，裁剪\n");
                    path=path.substring(path.indexOf(";")+1);//裁剪第一个
                    path+=picturePath+fileName+";";//加上新的
                    System.out.print( "path:"+path+"\n");
                }else{
                    path+=picturePath+fileName+";";//加上新的
                    pictureNum++;
                    System.out.print( "path:"+path+"\n");
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GymUpdateReqVO gymUpdateReqVO=new GymUpdateReqVO();
        BeanUtils.copyProperties(gym,gymUpdateReqVO);
        gymUpdateReqVO.setGymPicturesPath(path);
        gymService.updateGymInfo(gymUpdateReqVO);
        System.out.print("图片路径加载入数据库:"+path);
        model.addAttribute("loadPicture",1);
        return "gym/gym_list";
    }*/


    @GetMapping("/toPictureLoad/{gymId}")
    public String toPictureLoad(@PathVariable("gymId") String gymId, Model model){
        System.out.print("跳转gymId:"+gymId+"\n");
        model.addAttribute("gymId",gymId);
        return "/gym/pictureLoad";
    }
    @PostMapping("/loadGymPicture")
    public String loadGP(@RequestParam("picture") MultipartFile[] files, @RequestParam("gymId") String gymId, @RequestParam("pictureId") int id, Model model) throws IOException {
        System.out.print("图片存储id-gymId:"+"\n");
        System.out.print(id+"-"+gymId+"\n");
        model.addAttribute("gymId",gymId);
        Gym gym=gymService.getById(gymId);
        String path=gym.getGymPicturesPath();
        System.out.print("已有路径:"+path+"\n");

        List<String> existence=new ArrayList<>();
        while(path.length()>0){
            existence.add(path.substring(0,path.indexOf(";")));
            path=path.substring(path.indexOf(";")+1);
        }
        while(existence.size()<3){//填充
            existence.add(Constant.gym_defaultPicture);
        }
        if(existence.size()>0){
            System.out.print("当前已存在路径：\n");
            for(String s:existence){
                System.out.print(s+"\n");
            }
        }
        System.out.print("文件集："+files.length+"\n");
        for(MultipartFile file:files){
            if(file.isEmpty()){
                System.out.print("删除："+id+"\n");
                //将该图路径置空,即删除
                existence.set(id-1,"");
                break;
            }
            if(id>3){
                System.out.print("已存满，直接返回\n");
                break;
            }
            System.out.print("图片非空\n");
            OSS oss=new OSS();
            String picPath=oss.gymPictureLoad(file);
            System.out.print("图片存储地址:"+path+"\n");
            if(id-1>=existence.size()){
                existence.add(picPath);
            }else
                existence.set(id-1,picPath);//根据id替换新地址
            id++;
        }
        String newPath="";
        for(String s:existence){
            if(s.length()>0)
                newPath+=s+";";
        }
        //路径更新
        GymUpdateReqVO gymUpdateReqVO=new GymUpdateReqVO();
        BeanUtils.copyProperties(gym,gymUpdateReqVO);
        gymUpdateReqVO.setGymPicturesPath(newPath);
        System.out.print("更新存储:"+newPath+"\n");
        gymService.updateGymInfo(gymUpdateReqVO);
        return "/gym/pictureLoad";
    }

    @GetMapping("/login")
    public String login(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        return "home";
    }

    @GetMapping("/users/password")
    public String updatePassword(){
        return "users/update_password";
    }

    @GetMapping("/users/info")
    public String userDetail(Model model){
        model.addAttribute("flagType","edit");
        return "users/user_edit";
    }

    @GetMapping("/menus")
    public String menusList(){
        return "menus/menu_list";
    }

    @GetMapping("/blacklists")
    public String blackLists(){
        return "blacklists/blackuser_list";
    }

    @GetMapping("/roles")
    public String roleList(){
        return "roles/role_list";
    }

    @GetMapping("/users")
    public String userList(){
        return "users/user_list";
    }

    @GetMapping("/logs")
    public String logList(){
        return "logs/log_list";
    }

    @GetMapping("/loginlogs")
    public String loginlogList(){
        return "logs/loginlog_list";
    }

    @GetMapping("/gyms")
    public String gymList(){
        return "gym/gym_list";
    }

    @GetMapping("/gymHistories")
    public String gymHistroiesList(){
        return "gym/gym_historylist";
    }

    @GetMapping("/gymOrders")
    public String gymOrdersList(){
        return "gym/gym_orderlist";
    }

    @GetMapping("/gymComments")
    public String gymCommentsList(){
        return "gym/gym_commentslist";
    }

    @GetMapping("/gymCommentTags")
    public String gymCommentTagsList(){
        return "depts/dept_commenttaglist";
    }

    @GetMapping("/depts")
    public String deptList(){
        return "depts/dept_list";
    }

    @GetMapping("/403")
    public String error403(){
        return "error/403";
    }
    @GetMapping("/404")
    public String error404(){
        return "error/404";
    }

    @GetMapping("/500")
    public String error405(){
        return "error/500";
    }

    @GetMapping("/main")
    public String indexHome(){
        return "main";
    }

    @GetMapping("/vpnusers")
    public String vpnUsers(){
        return "vpn/vpnuser_list";
    }

    @GetMapping("/vpnnodes")
    public String vpnNodes(){
        return "vpn/vpnnode_list";
    }

    @GetMapping("/vpnuserflow")
    public String vpnUserFlow(){
        return "vpn/vpnuserflow_list";
    }

    @GetMapping("/server")
    public String server(){
        return "logs/server";
    }

    @GetMapping("/realshow")
    public String realShow(){
        return "show/real";
    }

    @GetMapping("/hotshow")
    public String hotShow(){
        return "show/hot";
    }
}
