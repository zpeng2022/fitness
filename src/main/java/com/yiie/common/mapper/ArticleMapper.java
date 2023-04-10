package com.yiie.common.mapper;

import com.yiie.entity.Article;
import com.yiie.entity.ExerciseType;
import com.yiie.vo.request.ArticlePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    List<Article> selectAllArticle(ArticlePageReqVO vo);

    int addArticle(Article article);

    int deletedArticle(@Param("article") Article article, @Param("list") List<String> userIds);

    Article selectByPrimaryKey(String articleId);

    int updateByPrimaryKeySelective(Article article);
}
