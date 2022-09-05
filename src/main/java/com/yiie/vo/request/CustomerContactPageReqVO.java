package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactPageReqVO {
    public CustomerContactPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

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

    @ApiModelProperty(value = "是否删除此条通讯录信息")
    private Integer contacter_deleted;

}
