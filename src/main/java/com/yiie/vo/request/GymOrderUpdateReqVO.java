package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
public class GymOrderUpdateReqVO {
    @NotBlank(message = "orderId不能为空")
    @ApiModelProperty(value = "orderId")
    private String orderId;

    @NotBlank(message = "场地Id不能为空")
    @ApiModelProperty(value = "gymId")
    private String gymId;

    @NotBlank(message = "机关单位Id不能为空")
    @ApiModelProperty(value = "deptId")
    private String deptId;

    @NotBlank(message = "用户Id不能为空")
    @ApiModelProperty(value = "customerId")
    private String customerId;

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty(value = "customer姓名")
    private String customerName;

    @NotBlank(message = "用户手机号不能为空")
    @ApiModelProperty(value = "customer手机号")
    private String customerPhone;

    @NotBlank(message = "用户身份证不能为空")
    @ApiModelProperty(value = "customer身份证号码")
    private String customerIdentityCard;

    @ApiModelProperty(value = "运动类型")
    private String exerciseType;
    /**
     * 如果存在禁入人员自动退回
     */
    @ApiModelProperty(value = "是否存在禁入人员")
    private Integer existBlackUser;

    /**
     * 0 未通过.
     * 1 通过.
     */
    @ApiModelProperty(value = "order状态,0未通过,1通过")
    private Integer orderStatus;
    /**
     * 机关单位那边来填写.
     * 退回的理由, 冗余设计吧, 万一这玩意要呢..
     */
    @ApiModelProperty(value = "预约失败详细回复")
    private String orderFailComment;
    /**
     * 随行人员的身份证和姓名.
     * 顺序是 一一对应
     * 分割符号, # (待定)
     */
    @ApiModelProperty(value = "其他用户身份证号码")
    private String otherCustomerIdentityCards;

    @ApiModelProperty(value = "其他用户姓名")
    private String otherCustomerNames;

    @ApiModelProperty(value = "其他用户电话")
    private String otherCustomerPhones;

    @ApiModelProperty(value = "预约的开始时间")
    private String orderStartTime;

    @ApiModelProperty(value = "预约的结束时间")
    private String orderEndTime;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "0表示删除, 1表示不删除")
    private Integer deleted;
}
