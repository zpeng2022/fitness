package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GymSearchNamePageReqVO {
    public GymSearchNamePageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "场地名称")
    private String gymName;
}
