package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.GymCommentService;
import com.yiie.common.service.GymCommentTagService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.Gym;
import com.yiie.entity.GymComments;
import com.yiie.enums.BaseResponseCode;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "组织模块-评价管理")
@RequestMapping("/sys")
@RestController
public class gymCommentController {
    @Autowired
    private GymCommentService gymCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private GymCommentTagService gymCommentTagService;

    @PostMapping("/readComment")
    @ApiOperation(value = "已读评论信息接口")
    @LogAnnotation(title = "评论管理", action = "已读评论信息")
    @RequiresPermissions("sys:gymComment:update")
    public DataResult readGymComments(@RequestBody @ApiParam(value = "评论id") String commentId, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        commentId=commentId.substring(1,commentId.length()-1);//去除commentId自带的""，否则导致sql语句失效
//        System.out.print("已读评论:"+commentId+"\n");
        GymComments gymComments=gymCommentService.getByCommentId(commentId);
//        System.out.print("评论："+gymComments.toString()+"\n");
        GymCommentUpdateReqVO newGym=new GymCommentUpdateReqVO();
        BeanUtils.copyProperties(gymComments,newGym);
        int status=gymComments.getCommentStatus();
//        System.out.print("评论当前状态:"+status+"\n");
        if(status==1){
//            System.out.print("评论已读\n");
            return DataResult.getResult(BaseResponseCode.READ_YES);
        }
        newGym.setCommentStatus(1);
//        System.out.print("修改评论状态:"+gymComments.toString()+"\n");
//        gymCommentService.readGymComments(gymComments);
        gymCommentService.updateGymCommentsInfo(newGym);
        return DataResult.success();
    }

    @PostMapping("/responseComment")
    @ApiOperation(value = "回复评论信息接口")
    @LogAnnotation(title = "评论回复", action = "回复评论信息")
    @RequiresPermissions("sys:gymComment:update")
    public DataResult responseGymComments(@RequestBody @ApiParam(value = "评论id与内容") Map<String, String> idAndContent, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String commentId=idAndContent.get("commentId");//去除commentId自带的""，否则导致sql语句失效
//        commentId=commentId.substring(1,commentId.length()-1);
        String commentAnswer=idAndContent.get("commentAnswer");
        System.out.print("回复Id:"+commentId+"\n");
        System.out.print("回复内容:"+commentAnswer+"\n");
        GymComments gymComments=gymCommentService.getByCommentId(commentId);
        GymCommentUpdateReqVO newGym=new GymCommentUpdateReqVO();
        BeanUtils.copyProperties(gymComments,newGym);
        newGym.setCommentAnswer(commentAnswer);
        gymCommentService.updateGymCommentsInfo(newGym);
        return DataResult.success();
    }

    // /sys/H5GymComments
    @PostMapping("/H5GymComments")
    @ApiOperation(value = "H5评论信息接口")
    @LogAnnotation(title = "评论管理",action = "分页获取评论信息列表")
    public DataResult<PageVO<GymComments>> h5PageInfo(@RequestBody GymCommentPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymComments>> result= DataResult.success();
//        if(vo.getCreateTime()!=null&&vo.getCreateTime()!=""){
//            String[] t=vo.getCreateTime().split(" ~ ");
//            vo.setCreateTime(t[0]);
//            vo.setUpdateTime(t[1]);
//        }
//        System.out.print("\n\n\n"+vo+"\n\n\n\n");
        result.setData(gymCommentService.h5PageInfo(vo));
        return result;
    }

    @PostMapping("/H5AddGymComment")
    @ApiOperation(value = "H5新增评论信息接口")
    @LogAnnotation(title = "评论管理",action = "新增评论信息")
    public DataResult addH5GymComments(@RequestBody @Valid GymCommentAddReqVO vo, HttpServletRequest request){
        // deptId...

        gymCommentService.addGymComments(vo);
        return DataResult.success();
    }

    @PostMapping("/gymComments")
    @ApiOperation(value = "评论信息接口")
    @LogAnnotation(title = "评论管理",action = "分页获取评论信息列表")
    @RequiresPermissions("sys:gymComment:list")
    public DataResult<PageVO<GymComments>> pageInfo(@RequestBody GymCommentPageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<GymComments>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        if(vo.getCreateTime()!=null&&vo.getCreateTime()!=""){
            String[] t=vo.getCreateTime().split(" ~ ");
            vo.setCreateTime(t[0]);
            vo.setUpdateTime(t[1]);
        }
        System.out.print("\n\n\n\n评论分页查询："+vo.toString()+"\n\n\n");
        result.setData(gymCommentService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/gymComment")
    @ApiOperation(value = "删除评论信息接口")
    @LogAnnotation(title = "评论管理", action = "删除评论信息")
    @RequiresPermissions("sys:gymComment:deleted")
    public DataResult deletedGymComments(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request){
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        gymCommentService.deletedGymComments(userIds);
        return DataResult.success();
    }

    @PostMapping("/gymComment")
    @ApiOperation(value = "新增评论信息接口")
    @LogAnnotation(title = "评论管理",action = "新增评论信息")
    @RequiresPermissions("sys:gymComment:add")
    public DataResult addGymComments(@RequestBody @Valid GymCommentAddReqVO vo, HttpServletRequest request){
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        // NOTICE: the deptId should belong to ...
        gymCommentService.addGymComments(vo);
        return DataResult.success();
    }

    @PutMapping("/gymComment")
    @ApiOperation(value = "更新评论信息接口")
    @LogAnnotation(title = "评论管理",action = "更新评论信息")
    @RequiresPermissions("sys:gymComment:update")
    public DataResult updateGymCommentsInfo(@RequestBody @Valid GymCommentUpdateReqVO vo, HttpServletRequest request){
        gymCommentService.updateGymCommentsInfo(vo);
        return DataResult.success();
    }

}
