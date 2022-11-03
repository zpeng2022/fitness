package com.yiie.common.controller;

//import com.sun.org.apache.xpath.internal.operations.Mod;
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

    @GetMapping("/toPictureLoad/{gymId}")
    public String toPictureLoad(@PathVariable("gymId") String gymId,Model model){
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
            String picPath=oss.saveLocal(file);
            System.out.print("图片存储地址:"+picPath+"\n");
            if(id-1>=existence.size()){
                existence.add(picPath);
            }else
                existence.set(id-1,picPath);//根据id替换新地址
            id++;
            // 文件上传后的路径
//            String fileName = file.getOriginalFilename();
//            String picturePath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\整体项目\\2022917\\fitness\\src\\main\\resources\\static\\pic\\";
            System.out.print("picPath:"+picPath+"\n");
            String originPath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pic\\";
            File dest = new File(originPath+picPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                System.out.print( "图片保存成功");
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @GetMapping("/monitor")
    public String monitor(){
        return "gym/monitor";
    }

    @GetMapping("/menus")
    public String menusList(){
        return "menus/menu_list";
    }

    @GetMapping("/allGymsDate")
    public String allGym(){
//        return "show/contain";
        return "gym/allGymsData";
    }
    @GetMapping("/allGymsDateFull")
    public String allGymFull(){
//        return "show/contain";
        return "gym/aGD_full";
    }

    @GetMapping("/contain")
    public String contain(){
//        return "show/contain";
        return "show/sz_main";
    }
    @GetMapping("/gymData_full")
    public String sz_main(){
//        return "show/contain";
        return "gym/gymData";
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
