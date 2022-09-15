package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GymCommentTagPageReqVO {

    public GymCommentTagPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "tagId")
    private String tagId;

    @ApiModelProperty(value = "deptId")
    private String deptId;

    @ApiModelProperty(value = "第几个tag")
    private Integer tagCount;

    @ApiModelProperty(value = "tag的内容")
    private String tagContent;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "0表示删除, 1表示不删除")
    private Integer deleted;
}
