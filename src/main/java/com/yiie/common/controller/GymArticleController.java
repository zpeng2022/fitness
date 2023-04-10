package com.yiie.common.controller;

import com.yiie.aop.annotation.LogAnnotation;
import com.yiie.common.service.ArticleService;
import com.yiie.common.service.UserService;
import com.yiie.constant.Constant;
import com.yiie.entity.Article;
import com.yiie.entity.Gym;
import com.yiie.utils.DataResult;
import com.yiie.utils.JwtTokenUtil;
import com.yiie.vo.data.GymIsClose;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "组织模块-场馆文章管理")
@RequestMapping("/sys")
@RestController
public class GymArticleController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @DeleteMapping("/article")
    @ApiOperation(value = "删除场馆文章接口")
    @LogAnnotation(title = "场馆文章管理", action = "删除场馆文章")
    @RequiresPermissions("sys:article:deleted")
    public DataResult deletedArticle(@RequestBody @ApiParam(value = "id集合") List<String> userIds, HttpServletRequest request){
        System.out.print("\n\n删除\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        articleService.deletedArticle(userIds,operationId);
        return DataResult.success();
    }

    @PostMapping("/article")
    @ApiOperation(value = "新增场馆文章接口")
    @LogAnnotation(title = "场馆文章管理",action = "新增场馆文章")
    @RequiresPermissions("sys:article:add")
    public DataResult addArticle(@RequestBody @Valid ArticleAddReqVO vo, HttpServletRequest request){
        System.out.print("\n\n新增:"+vo+"\n\n");
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptID = userService.getDeptIdFromUserId(userId);
        vo.setDeptId(deptID);
        vo.setAuthorId(userId);
        articleService.addArticle(vo);
        return DataResult.success();
    }

    @PostMapping("/articles")
    @ApiOperation(value = "场馆文章接口")
    @LogAnnotation(title = "场馆管理",action = "分页获取场馆文章列表")
    @RequiresPermissions("sys:article:list")
    public DataResult<PageVO<Article>> pageInfo(@RequestBody ArticlePageReqVO vo, HttpServletRequest request){
        DataResult<PageVO<Article>> result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String deptId= userService.getDeptIdFromUserId(userId);
        System.out.print("场馆文章接口vo:\n"+vo+"\n\n\n\n");
        if(deptId!=null&&deptId.length()!=0&&deptId!="")
            vo.setDeptId(deptId);
        System.out.print("场馆文章接口vo:\n"+vo+"\n\n\n\n");
        PageVO<Article> pageVO=articleService.pageInfo(vo);
        result.setData(pageVO);
        return result;
    }

    @PutMapping("/article")
    @ApiOperation(value = "更新场馆文章接口")
    @LogAnnotation(title = "场馆文章管理",action = "更新场馆文章")
    @RequiresPermissions("sys:article:update")
    public DataResult updateBlackUserInfo(@RequestBody @Valid ArticleUpdateReqVO vo, HttpServletRequest request){
        System.out.print("\n\n更新\n\n");
        String operationId= JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        System.out.print("\n\nvo:"+vo);
        articleService.updateArticle(vo,operationId);
        return DataResult.success();
    }
}
