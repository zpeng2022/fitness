package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerContactAddReqVO {
    @ApiModelProperty(value = "通讯录id")
    @NotBlank(message = "通讯录id不能为空")
    private String contact_id;

    @ApiModelProperty(value = "用户的通讯录唯一id")
    @NotBlank(message = "用户的通讯录唯一id不能为空")
    private String customer_contacts_id;

    @ApiModelProperty(value = "通讯录内用户名称")
    private String contacter_display_name;

    @ApiModelProperty(value = "通讯录内用户身份证")
    private String contacter_identity_card;

    @ApiModelProperty(value = "通讯录内用户手机号")
    private String contacter_phone;

    @ApiModelProperty(value = "是否删除此条通讯录信息")
    private Integer contacter_deleted;
}
