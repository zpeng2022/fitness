package com.yiie.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlackUserInfoRespVO {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "身份证号码")
    private String identityCard;
    @ApiModelProperty(value = "姓名")
    private String username;
    @ApiModelProperty(value = "部门ID")
    private String deptID;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "类型")
    private String typeInfo;
    @ApiModelProperty(value = "导入时间")
    private String importTime;
    @ApiModelProperty(value = "导入时间")
    private String updateTime;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "删除")
    private String deleted;
}
