package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlackTypeAddReqVO {
    @ApiModelProperty(value = "id")
    private String typeId;

    @ApiModelProperty(value = "导入时间")
    private String typeName;

    @ApiModelProperty(value = "导入时间")
    private String description;

    @ApiModelProperty(value = "导入时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除")
    private Integer deleted;
}
