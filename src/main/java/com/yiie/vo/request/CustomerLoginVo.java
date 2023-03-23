package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerLoginVo {

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "不能为空")
    private String name
            ;
    @ApiModelProperty(value = "身份证")
    @NotBlank(message = "不能为空")
    private String identityCard;

    @ApiModelProperty(value = "登录类型(1:pc;2:App)")
    @NotBlank(message = "登录类型不能为空")
    private String type;
}
