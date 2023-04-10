package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.CustomerService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.request.CustomerAddReqVO;
import com.yiie.vo.request.CustomerLoginVo;
import com.yiie.vo.request.LoginReqVO;
import com.yiie.vo.response.LoginRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Api(tags = "用户接口")
@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/user/sportIndex")
    @ApiOperation(value="customer运动指数")
    public DataResult sportIndex(HttpServletRequest request) {
        double index=0;
        //得到运动数据
        //customerService.getSport(id);
        //进行指数计算并返回
        return DataResult.success(index);
    }

    @PostMapping("/user/register")
    @ApiOperation(value="customer注册")
    public DataResult register(@RequestBody CustomerAddReqVO customerAddReqVO) throws IOException {
        customerService.addCustomer(customerAddReqVO);
        return DataResult.success();
    }

    @PostMapping("/user/contact")
    @ApiOperation(value = "H5端用户进入系统")
    public String contact(HttpServletRequest request){//这个东西要放在indexController，用@Controller注解
        //尝试获取customerId
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
//        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        String customerId= JwtTokenUtil.getUserId(accessToken);
        if(customerId==null){
            return "customer/login";//跳转登录页面
        }else
            return "customer/main";//进入主页面或是返回数据
    }

    @PostMapping("/user/login")
    @ApiOperation(value = "H5用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody CustomerLoginVo customerLoginVo){
        DataResult<LoginRespVO> result=DataResult.success();
        customerLoginVo.setType("2");
        result.setData(customerService.login(customerLoginVo));
        return result;
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    @LogAnnotation(title = "customer管理",action = "退出")
    public DataResult logout(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        customerService.logout(accessToken,refreshToken);
        return DataResult.success();
    }
    @GetMapping("/user/token")
    @ApiOperation(value = "用户刷新token接口")
    @LogAnnotation(title = "用户管理",action = "用户刷新token")
    public DataResult<String> refreshToken(HttpServletRequest request){
        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        DataResult<String> result=DataResult.success();
        result.setData(customerService.refreshToken(refreshToken,accessToken));
        return result;
    }
}
