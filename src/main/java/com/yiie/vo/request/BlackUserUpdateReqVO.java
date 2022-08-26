package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class BlackUserUpdateReqVO {
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String id;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户身份证能为空")
    private String identityCard;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "人员类型信息")
    private String typeInfo;

    @ApiModelProperty(value = "导入时间")
    private Integer importTime;

    @ApiModelProperty(value = "更新时间")
    private Integer updateTime;

    @ApiModelProperty(value = "性别(1.男 2.女 3.保密 ")
    private Integer sex;
}
