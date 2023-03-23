package com.yiie.entity;

import com.yiie.vo.response.FollowsInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 预约表, 用于审核
 * 自动审核, 则显示的时候直接通过即可.
 */
public class GymOrder implements Serializable {
    private String orderId;
    private String gymId;
    private String deptId;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private String customerIdentityCard;
    private String exerciseType;
    /**
     * 如果存在禁入人员自动退回
     * 1 表示有
     * 0 表示没有
     */
    private Integer existBlackUser;
    /**
     * 0 未通过.
     * 1 通过.
     */
    private Integer orderStatus;
    /**
     * 机关单位那边来填写.
     * 退回的理由, 冗余设计吧, 万一这玩意要呢..
     */
    private String orderFailComment;
    /**
     * 随行人员的身份证和姓名.
     * 顺序是 一一对应
     * 分割符号, # (待定)
     */
    private String otherCustomerIdentityCards;
    private String otherCustomerNames;
    private String otherCustomerPhones;
    private String otherIsBlack;
    private Date orderStartTime;
    private Date orderEndTime;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

    private Gym gym;

    @ApiModelProperty("机关单位")
    private Dept dept;

    private Integer readStatus;

    private String cancelReason;

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    @Override
    public String toString() {
        return "GymOrder{" +
                "orderId='" + orderId + '\'' +
                ", gymId='" + gymId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerIdentityCard='" + customerIdentityCard + '\'' +
                ", exerciseType='" + exerciseType + '\'' +
                ", existBlackUser=" + existBlackUser +
                ", orderStatus=" + orderStatus +
                ", orderFailComment='" + orderFailComment + '\'' +
                ", otherCustomerIdentityCards='" + otherCustomerIdentityCards + '\'' +
                ", otherCustomerNames='" + otherCustomerNames + '\'' +
                ", otherCustomerPhones='" + otherCustomerPhones + '\'' +
                ", otherIsBlack='" + otherIsBlack + '\'' +
                ", orderStartTime=" + orderStartTime +
                ", orderEndTime=" + orderEndTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getOtherIsBlack() {
        return otherIsBlack;
    }

    public void setOtherIsBlack(String otherIsBlack) {
        this.otherIsBlack = otherIsBlack;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId == null ? null : gymId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public String getCustomerIdentityCard() {
        return customerIdentityCard;
    }

    public void setCustomerIdentityCard(String customerIdentityCard) {
        this.customerIdentityCard = customerIdentityCard == null ? null : customerIdentityCard.trim();
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType == null ? null : exerciseType.trim();
    }

    public Integer getExistBlackUser() {
        return existBlackUser;
    }

    public void setExistBlackUser(Integer existBlackUser) {
        this.existBlackUser = existBlackUser;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderFailComment() {
        return orderFailComment;
    }

    public void setOrderFailComment(String orderFailComment) {
        this.orderFailComment = orderFailComment == null ? null : orderFailComment.trim();
    }

    public String getOtherCustomerIdentityCards() {
        return otherCustomerIdentityCards;
    }

    public void setOtherCustomerIdentityCards(String otherCustomerIdentityCards) {
        this.otherCustomerIdentityCards = otherCustomerIdentityCards == null ? null : otherCustomerIdentityCards.trim();
    }

    public String getOtherCustomerNames() {
        return otherCustomerNames;
    }

    public void setOtherCustomerNames(String otherCustomerNames) {
        this.otherCustomerNames = otherCustomerNames == null ? null : otherCustomerNames.trim();
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime ;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getOtherCustomerPhones() {
        return otherCustomerPhones;
    }

    public void setOtherCustomerPhones(String otherCustomerPhones) {
        this.otherCustomerPhones = otherCustomerPhones;
    }

}
