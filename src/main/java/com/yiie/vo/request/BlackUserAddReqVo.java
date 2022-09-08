package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import javax.validation.constraints.NotBlank;

/**
 * 根据前端的信息来填写
 */
@Data
public class BlackUserAddReqVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "身份证号码不能为空")
    private String identityCard;

    @ApiModelProperty(value = "部门ID")
    private String deptID;

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
