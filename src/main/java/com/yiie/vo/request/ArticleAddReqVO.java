package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleAddReqVO {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章标题")
    private String summary;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章内容")
    private String textContent;

    @ApiModelProperty(value = "文章内容")
    private String deptId;

    @ApiModelProperty(value = "文章内容")
    private String authorId;
}
