package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CustomerAddReqVO {
    @ApiModelProperty(value = "场馆用户id")
    @NotBlank(message = "场馆用户id不能为空")
    private String customer_id;

    @ApiModelProperty(value = "用户身份证")
    @NotBlank(message = "用户身份证不能为空")
    private String customer_identity_card;

    @ApiModelProperty(value = "用户名称")
    private String customer_name;

    @ApiModelProperty(value = "用户昵称")
    private String customer_nickname;

    @ApiModelProperty(value = "用户体重")
    private Double customer_weight;

    @ApiModelProperty(value = "用户身高")
    private Double customer_height;

    @ApiModelProperty(value = "用户生日")
    private Date customer_birthday;

    @ApiModelProperty(value = "用户居住地址")
    private String customer_location;

    @ApiModelProperty(value = "用户爱好")
    private String customer_hobby;

    @ApiModelProperty(value = "用户疾病历史")
    private String customer_disease;

    @ApiModelProperty(value = "用户头像")
    private String customer_picture;

    @ApiModelProperty(value = "用户性别")
    private Integer customer_gender;

    @ApiModelProperty(value = "用户手机号")
    private String customer_phone;

    @ApiModelProperty(value = "用户健身码")
    @NotBlank(message = "用户健身码不能为空")
    private String customer_fitness_QRCode;

    @ApiModelProperty(value = "常用联系人唯一ID")
    @NotBlank(message = "常用联系人唯一ID不能为空")
    private String customer_contacts_id;

    @ApiModelProperty(value = "用户详细信息表id")
    @NotBlank(message = "用户详细信息表id不能为空")
    private String customer_info_id;

    @ApiModelProperty(value = "用户创建时间")
    private Date customer_create_time;

    @ApiModelProperty(value = "用户表更新时间")
    private Date customer_update_time;

    @ApiModelProperty(value = "此用户记录是否删除")
    private Integer customer_deleted;
}
