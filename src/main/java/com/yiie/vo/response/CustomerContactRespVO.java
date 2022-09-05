package com.yiie.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactRespVO {
    @ApiModelProperty(value = "通讯录id")
    private String contact_id;

    @ApiModelProperty(value = "用户的通讯录唯一id")
    private String customer_contacts_id;

    @ApiModelProperty(value = "通讯录内用户名称")
    private String contacter_display_name;

    @ApiModelProperty(value = "通讯录内用户身份证")
    private String contacter_identity_card;

    @ApiModelProperty(value = "通讯录内用户手机号")
    private String contacter_phone;
}
