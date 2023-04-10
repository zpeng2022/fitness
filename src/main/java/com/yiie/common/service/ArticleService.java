package com.yiie.common.service;

import com.yiie.entity.Article;
import com.yiie.vo.request.ArticleAddReqVO;
import com.yiie.vo.request.ArticlePageReqVO;
import com.yiie.vo.request.ArticleUpdateReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface ArticleService {
    PageVO<Article> pageInfo(ArticlePageReqVO vo);

    void addArticle(ArticleAddReqVO vo);

    void deletedArticle(List<String> userIds, String operationId);

    void updateArticle(ArticleUpdateReqVO vo, String operationId);
}
