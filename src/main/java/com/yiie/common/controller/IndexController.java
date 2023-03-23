package com.yiie.common.controller;

//import com.sun.org.apache.xpath.internal.operations.Mod;
//import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.CustomerService;
import com.yiie.common.service.GymService;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.entity.H5Background;
import com.yiie.enums.BaseResponseCode;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.utils.OSS;
import com.yiie.vo.request.GymUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
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
import java.util.Date;
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
    @Autowired
    private CustomerService customerService;

    @GetMapping("/physical")
    public String physical(){
        return "physical/physical";
    }

    @GetMapping("/H5_background")
    public String H5_background(){
        return "h5/background";
    }

    @PostMapping("/changeH5Background")
    @ApiOperation(value = "修改主页背景接口")
    @LogAnnotation(title = "修改主页背景",action = "修改主页背景")
    public String changeH5Background(@RequestParam("picture") MultipartFile[] files){
        H5Background h5Background=customerService.getBackground();
        for(MultipartFile file:files){
            if(file.isEmpty()){
                throw new IllegalArgumentException("文件为空");
            }
            //转base64存储
            String base64EncoderImg = "";
            try {
                base64EncoderImg = Base64.encodeBase64String(file.getBytes());
                base64EncoderImg = "data:image/png;base64," + base64EncoderImg;
//                System.out.println(base64EncoderImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String picPath=base64EncoderImg;
            if(h5Background==null){//初始无记录
                h5Background=new H5Background();
                h5Background.setId("1");
                h5Background.setBackground(picPath);
                h5Background.setCreatetime(new Date());
                customerService.insetBackground(h5Background);
            }else {
                h5Background.setBackground(picPath);
                h5Background.setUpdatetime(new Date());
                customerService.changeBackground(h5Background);
            }
        }
        return "h5/background";
    }


    @GetMapping("/gym_articleList")
    public String gym_articles(){
        return "gym/gym_articlelist";
    }

    @GetMapping("/toPictureLoad/{gymId}")
    public String toPictureLoad(@PathVariable("gymId") String gymId,Model model){
        System.out.print("跳转gymId:"+gymId+"\n");
        model.addAttribute("gymId",gymId);
        return "gym/pictureLoad";

    }
    @PostMapping("/loadGymPicture")
    public String loadGP(@RequestParam("picture") MultipartFile[] files, @RequestParam("gymId") String gymId, @RequestParam("pictureId") int id, Model model) throws IOException {
        System.out.print("图片存储id-gymId:"+"\n");
        System.out.print(id+"-"+gymId+"\n");
        model.addAttribute("gymId",gymId);
        Gym gym=gymService.getById(gymId);
        String path=gym.getGymPicturesPath();
//        System.out.print("已有路径:"+path+"\n");

        List<String> existence=new ArrayList<>();
        if(path==null)
            path="";
        while(path.length()>0){
            /*existence.add(path.substring(0,path.indexOf(";")));
            path=path.substring(path.indexOf(";")+1);*/
            existence.add(path.substring(0,path.indexOf("|")));
            path=path.substring(path.indexOf("|")+1);
        }
        while(existence.size()<3){//填充
//            existence.add(Constant.gym_defaultPicture);
            existence.add(Constant.gym_defaultPictureBase64);
        }
        /*if(existence.size()>0){
            System.out.print("当前已存在路径：\n");
            for(String s:existence){
                System.out.print(s+"\n");
            }
        }*/
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
           /* OSS oss=new OSS();
            String picPath=oss.saveLocal(file);*/
            //转base64存储
            String base64EncoderImg = "";
            try {
                base64EncoderImg = Base64.encodeBase64String(file.getBytes());
                base64EncoderImg = "data:image/png;base64," + base64EncoderImg;
//                System.out.println(base64EncoderImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String picPath=base64EncoderImg;


//            System.out.print("图片存储地址:"+picPath+"\n");
            if(id-1>=existence.size()){
                existence.add(picPath);
            }else
                existence.set(id-1,picPath);//根据id替换新地址
            id++;
            // 文件上传后的路径
//            String fileName = file.getOriginalFilename();
//            String picturePath="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\整体项目\\2022917\\fitness\\src\\main\\resources\\static\\pic\\";
//            System.out.print("picPath:"+picPath+"\n");
//            String originPath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pic\\";
            //原存储代码
            /*String originPath="/var/bgman/upload/";
            System.out.println("\n\n图片路径：" + originPath+picPath+"\n\n\n");
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
            }*/
        }
        String newPath="";
        for(String s:existence){
            if(s.length()>0){
//                newPath+=s+";";
                newPath+=s+"|";
            }
        }
        //路径更新
        GymUpdateReqVO gymUpdateReqVO=new GymUpdateReqVO();
        BeanUtils.copyProperties(gym,gymUpdateReqVO);
        gymUpdateReqVO.setGymPicturesPath(newPath);
//        System.out.print("更新存储:"+newPath+"\n");
//        System.out.print("\n\n更新存储:"+gymUpdateReqVO.toString()+"\n\n");
        gymService.updateGymInfo(gymUpdateReqVO);
        return "gym/pictureLoad";
    }

    @GetMapping("/exerciseTypeList")
    public String exerciseTypeList(){
        System.out.print("运动类型信息\n");
        return "exercise/exerciseTypeList";
    }
    @GetMapping("/blackTypeList")
    public String blackTypeList(){
        System.out.print("黑名单类型信息\n");
        return "black/blackTypeList";
    }
    @GetMapping("/gymInformation")
    public String gymInformation(){
        System.out.print("场馆信息\n");
        return "gym/gymInfo";
    }

    @GetMapping("/login")
    public String login(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        return "show/sz_main";
    }
    @GetMapping("/tohome")
    public String tohome(Model model, HttpServletRequest request){
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
        return "gym/gymData";
    }

    @GetMapping("/gymData_full/{gymName}")
    public String sz_main22(@PathVariable("gymName") String GymName,Model model){
//        return "show/contain";
        model.addAttribute("selectName",GymName);
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
