package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.ArticleMapper;
import com.yiie.common.service.ArticleService;
import com.yiie.entity.Article;
import com.yiie.entity.ExerciseType;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.ArticleAddReqVO;
import com.yiie.vo.request.ArticlePageReqVO;
import com.yiie.vo.request.ArticleUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public PageVO<Article> pageInfo(ArticlePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<Article> articles = articleMapper.selectAllArticle(vo);
        return PageUtils.getPageVO(articles);
    }

    @Override
    public void addArticle(ArticleAddReqVO vo) {
        Article article=new Article();
        BeanUtils.copyProperties(vo,article);
        article.setId(UUID.randomUUID().toString());
        article.setCreateTime(new Date());
        article.setDeleted(1);
        int i = articleMapper.addArticle(article);
        if (i!=1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedArticle(List<String> userIds, String operationId) {
        Article article=new Article();
        article.setUpdateTime(new Date());
        article.setDeleted(0);
        int i = articleMapper.deletedArticle(article,userIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateArticle(ArticleUpdateReqVO vo, String operationId) {
        Article article = articleMapper.selectByPrimaryKey(vo.getArticleId());
        if (null == article){
            log.error("传入 的 id:{}不合法",vo.getArticleId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,article);
        article.setDeleted(1);
        article.setUpdateTime(new Date());
        int count= articleMapper.updateByPrimaryKeySelective(article);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
