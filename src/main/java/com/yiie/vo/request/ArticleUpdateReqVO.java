package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleUpdateReqVO {

    @ApiModelProperty(value = "articleId")
//    @NotBlank(message = "typeId不能为空")
    private String articleId;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "名称")
    private String summary;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章内容")
    private String textContent;

    @ApiModelProperty(value = "名称")
    private String authorId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除")
    private Integer deleted;
}
