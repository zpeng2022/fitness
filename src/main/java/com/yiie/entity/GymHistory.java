package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 进出历史
 * 进入一个人之后在gymCount添加计数
 * 因为线下人员不一定有commentId,
 * 之后可以根据identityCard查询
 * 写入前需要查询是否为
 */
public class GymHistory implements Serializable {
    private String historyId;
    private String gymId;
    /**
     * 所属的机关单位Id, 显示的时候需要
     */
    private String deptId;
    private String orderId;

    private String customerName;
    private String customerPhone;
    private String customerIdentityCard;
    /**
     * TODO: 后端的任务
     * 获取机关单位id.
     * 查询禁入名单.
     * 0 非禁入名单用户
     * 1 禁入名单用户
     */
    private Integer isBlackUser;
    /**
     * TODO: 后端的任务
     * 查询是否为 当天 的预约用户.
     * orderTime, exerciseType也是
     * 通过order来查询.
     * 线下的exerciseType和orderTime
     * 按照默认设置(空?)
     * GymOrderDetail..
     * 0 线下人员
     * 1 线上预约人员
     */
    private Integer isOnlineUser;
    /**
     * orderTime是预约要过去的时间
     */
    private Date orderStartTime;
    private Date orderEndTime;
    private String exerciseType;
    /**
     * 进出场馆的时间
     */
    private Date inTime;
    private Date outTime;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId == null ? null : historyId.trim();
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

    public Integer getIsBlackUser() {
        return isBlackUser;
    }

    public void setIsBlackUser(Integer isBlackUser) {
        this.isBlackUser = isBlackUser;
    }

    public Integer getIsOnlineUser() {
        return isOnlineUser;
    }

    public void setIsOnlineUser(Integer isOnlineUser) {
        this.isOnlineUser = isOnlineUser;
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

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType == null ? null : exerciseType.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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

    @Override
    public String toString() {
        return "GymHistory{" +
                "historyId='" + historyId + '\'' +
                ", gymId='" + gymId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerIdentityCard='" + customerIdentityCard + '\'' +
                ", isBlackUser=" + isBlackUser +
                ", isOnlineUser=" + isOnlineUser +
                ", orderStartTime=" + orderStartTime +
                ", orderEndTime=" + orderEndTime +
                ", exerciseType='" + exerciseType + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
