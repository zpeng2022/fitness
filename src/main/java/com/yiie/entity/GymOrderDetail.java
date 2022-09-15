package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用来判断用户是否为线上用户
 * 匹配到了就是线上
 * Order通过之后才会写入到数据库...
 */
public class GymOrderDetail implements Serializable {
    private String orderDetailId;
    private String gymId;
    private String deptId;
    /**
     * 哪个order过来的记录.
     */
    private String orderId;
    private String customerName;
    private String customerPhone;
    private String customerIdentityCard;
    private Integer isBlackUser;
    private String exerciseType;
    private Date orderStartTime;
    private Date orderEndTime;
    private Date createTime;
    private Integer deleted;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerIdentityCard() {
        return customerIdentityCard;
    }

    public void setCustomerIdentityCard(String customerIdentityCard) {
        this.customerIdentityCard = customerIdentityCard;
    }

    public Integer getIsBlackUser() {
        return isBlackUser;
    }

    public void setIsBlackUser(Integer isBlackUser) {
        this.isBlackUser = isBlackUser;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "GymOrderDetail{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", gymId='" + gymId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerIdentityCard='" + customerIdentityCard + '\'' +
                ", isBlackUser=" + isBlackUser +
                ", exerciseType='" + exerciseType + '\'' +
                ", orderStartTime=" + orderStartTime +
                ", orderEndTime=" + orderEndTime +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                '}';
    }
}
