package com.yiie.vo.request;

import com.yiie.vo.response.FollowsInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GymOrderPageReqVO {
    public GymOrderPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "orderId")
    private String orderId;

    @ApiModelProperty(value = "gymId")
    private String gymId;

    @ApiModelProperty(value = "deptId")
    private String deptId;

    @ApiModelProperty(value = "customerId")
    private String customerId;

    @ApiModelProperty(value = "customer姓名")
    private String customerName;

    @ApiModelProperty(value = "customer手机号")
    private String customerPhone;

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
    @ApiModelProperty(value = "随行是否禁入")
    private String otherIsBlack;

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

    @ApiModelProperty(value = "随行者的数据总和")
    private List<FollowsInfo> followsInfo;

}
