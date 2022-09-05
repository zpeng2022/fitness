package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerInfoUpdateReqVO {
    @ApiModelProperty(value = "场馆用户id")
    @NotBlank(message = "场馆用户id不能为空")
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
