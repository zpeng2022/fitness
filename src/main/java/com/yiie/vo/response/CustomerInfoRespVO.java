package com.yiie.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  the resp VO for CustomerInfo class
 */

@Data
public class CustomerInfoRespVO {
    @ApiModelProperty(value = "场馆用户id")
    private String customer_info_id;

    @ApiModelProperty(value = "用户健身积分")
    private Integer customer_fitness_point;

    @ApiModelProperty(value = "用户信用积分")
    private Integer customer_credit_point;

    @ApiModelProperty(value = "用户每日步数")
    private Integer customer_step_per_day;

}
