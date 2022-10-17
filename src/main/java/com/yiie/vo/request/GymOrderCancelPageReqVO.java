package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GymOrderCancelPageReqVO {
    @ApiModelProperty(value = "orderId")
    private String orderId;
}
