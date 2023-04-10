package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticlePageReqVO {

    public ArticlePageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "articleId")
    private String articleId;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "名称")
    private String authorId;

    @ApiModelProperty(value = "名称")
    private String authorName;

    @ApiModelProperty(value = "机关单位")
    private String deptId;

    @ApiModelProperty(value = "导入时间")
    private String startTime;

    @ApiModelProperty(value = "导入时间")
    private String endTime;

    @ApiModelProperty(value = "deleted")
    private Integer deleted;
}
