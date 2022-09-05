package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerInfoPageReqVO {
    public CustomerInfoPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "场馆用户id")
    private String customer_info_id;

    @ApiModelProperty(value = "用户健身积分")
    private Integer customer_fitness_point;

    @ApiModelProperty(value = "用户信用积分")
    private Integer customer_credit_point;

    @ApiModelProperty(value = "用户每日步数")
    private Integer customer_step_per_day;

    @ApiModelProperty(value = "是否删除此条记录")
    private Integer customer_info_deleted;
}
