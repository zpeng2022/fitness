package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymOrderService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.GymHistory;
import com.yiie.entity.GymOrder;
import com.yiie.enums.BaseResponseCode;
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

@Api(tags = "组织模块-预约管理")
@RequestMapping("/sys")
@RestController
public class gymOrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private GymOrderService gymOrderService;

    @PostMapping("/passOrder")
    @ApiOperation(value = "通过预约接口")
    @LogAnnotation(title = "通过预约",action = "通过")
    public DataResult passOrder(@RequestBody String orderId, HttpServletRequest request){
        if(orderId==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
//        System.out.print("通过预约:"+orderId+"\n\n");//orderId带""
        gymOrderService.passOrder(orderId.replaceAll("\"",""));//去掉""
        return DataResult.success();
    }
    @PostMapping("/refuseOrder")
    @ApiOperation(value = "拒绝预约接口")
    @LogAnnotation(title = "拒绝预约",action = "拒绝")
    public DataResult refuseOrder(@RequestBody OrderCancelVO cancelVO, HttpServletRequest request){
        System.out.print("\n\n\n拒绝预约:"+cancelVO+"\n\n");
        if(cancelVO.getOrderId()==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
//        System.out.print("通过预约:"+orderId+"\n\n");//orderId带""
        gymOrderService.refuseOrder2(cancelVO);//去掉""
        return DataResult.success();
    }
    @PostMapping("/cancelOrder")
    @ApiOperation(value = "取消预约接口")
    @LogAnnotation(title = "取消预约",action = "取消")
    public DataResult cancelOrder(@RequestBody OrderCancelVO cancelVO, HttpServletRequest request){
//        System.out.print("\n\n\n取消预约:"+cancelVO+"\n\n");
        if(cancelVO.getGymId()==null||cancelVO.getTimespan()==null)
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO);
//        System.out.print("通过预约:"+orderId+"\n\n");//orderId带""
        String time=cancelVO.getTimespan();
        String start=time.split(" - ")[0];
        String end=time.split(" - ")[1];
        cancelVO.setStart(start);
        cancelVO.setEnd(end);
        System.out.print("\n\n\n取消预约:"+cancelVO+"\n\n");
        gymOrderService.cancelOrder(cancelVO);//去掉""
        return DataResult.success();
    }

    @PostMapping("/H5UnreadCancelOrders")
    @ApiOperation(value = "查询未读被取消预约接口")
    @LogAnnotation(title = "查询未读被取消预约",action = "查询")
    public DataResult getUnreadCanceledOrder(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        DataResult<List<GymOrder>> result= DataResult.success();
        if(userId==null){
            return DataResult.getResult(BaseResponseCode.IdNULL);
        }
        List<GymOrder> list=gymOrderService.getUnreadCanceledOrderByCustomerId(userId);
        result.setData(list);
        return result;
    }
    @PostMapping("/H5AllCancelOrders")
    @ApiOperation(value = "查询全部被取消预约接口")
    @LogAnnotation(title = "查询全部被取消预约",action = "查询")
    public DataResult getAllCanceledOrder(HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        DataResult<List<GymOrder>> result= DataResult.success();
        if(userId==null){
            return DataResult.getResult(BaseResponseCode.IdNULL);
        }
        List<GymOrder> list=gymOrderService.getCanceledOrderByCustomerId(userId);
        result.setData(list);
        return result;
    }
    @PostMapping("/H5ReadCancelOrders")
    @ApiOperation(value = "已读被取消预约接口")
    @LogAnnotation(title = "已读被取消预约",action = "已读")
    public DataResult readCanceledOrder(@RequestBody String orderId){
//        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(orderId==null){
            return DataResult.getResult(BaseResponseCode.IdNULL);
        }
        gymOrderService.readCanceledOrder(orderId);
        return DataResult.success();
    }
    @PostMapping("/H5DelCancelOrders")
    @ApiOperation(value = "删除被取消预约接口")
    @LogAnnotation(title = "删除被取消预约",action = "已读")
    public DataResult delCanceledOrder(@RequestBody String orderId){
//        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        if(orderId==null){
            return DataResult.getResult(BaseResponseCode.IdNULL);
        }
        gymOrderService.delCanceledOrder(orderId);
        return DataResult.success();
    }
    @PostMapping("/H5GymOrders")
    @ApiOperation(value = "H5预约记录接口")
    @LogAnnotation(title = "预约管理",action = "分页获取预约信息列表")
    public DataResult<PageVO<GymOrder>> H5PageInfo(@RequestBody GymOrderPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymOrder>> result= DataResult.success();
        // vo.setDeptId(deptID);
        result.setData(gymOrderService.h5PageInfo(vo));
        return result;
    }

    @PostMapping("/H5AddGymOrders")
    @ApiOperation(value = "H5新增预约记录接口")
    @LogAnnotation(title = "预约管理",action = "H5新增预约记录接口")
    public DataResult H5AddGymOrders(@RequestBody @Valid GymOrderAddReqVO vo, HttpServletRequest request){
        gymOrderService.addGymOrders(vo);
        return DataResult.success();
    }

    @PostMapping("/H5CancelGymOrder")
    @ApiOperation(value = "H5取消预约记录接口")
    @LogAnnotation(title = "预约管理",action = "H5取消预约记录接口")
    public DataResult H5CancelGymOrder(@RequestBody @Valid GymOrderCancelPageReqVO vo, HttpServletRequest request){
        gymOrderService.cancelGymOrder(vo);
        return DataResult.success();
    }

    @PostMapping("/gymOrders")
    @ApiOperation(value = "预约记录接口")
    @LogAnnotation(title = "预约管理",action = "分页获取预约信息列表")
    @RequiresPermissions("sys:gymOrder:list")
    public DataResult<PageVO<GymOrder>> pageInfo(@RequestBody GymOrderPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymOrder>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId = userService.getDeptIdFromUserId(userId);
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        System.out.print("预约管理：\n"+vo+"\n");
        result.setData(gymOrderService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymOrder")
    @ApiOperation(value = "删除预约记录接口")
    @LogAnnotation(title = "预约管理", action = "删除预约信息列表")
    @RequiresPermissions("sys:gymOrder:deleted")
    public DataResult deletedGymOrders(@RequestBody @ApiParam(value = "用户id集合") List<String> orderIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymOrderService.deletedGymOrders(orderIds);
        return DataResult.success();
    }

    @PostMapping("/gymOrder")
    @ApiOperation(value = "新增预约记录接口")
    @LogAnnotation(title = "预约管理",action = "新增预约信息列表")
    @RequiresPermissions("sys:gymOrder:add")
    public DataResult addGymOrders(@RequestBody @Valid GymOrderAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymOrderService.addGymOrders(vo);
        return DataResult.success();
    }

    @PutMapping("/gymOrder")
    @ApiOperation(value = "更新预约记录接口")
    @LogAnnotation(title = "预约管理",action = "更新预约信息列表")
    @RequiresPermissions("sys:gymOrder:update")
    public DataResult updateGymOrdersInfo(@RequestBody @Valid GymOrderUpdateReqVO vo, HttpServletRequest request){
        gymOrderService.updateGymOrdersInfo(vo);
        return DataResult.success();
    }
}
