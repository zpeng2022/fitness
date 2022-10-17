package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

public class CustomerRecord implements Serializable {
    private String recordId;
    private String customerId;
    private Integer isOnline;
    private Integer recordStatus;
    private Date orderStartTime;
    private Date orderEndTime;
    private Date inGymTime;
    private Date outGymTime;
    private String exerciseType;
    private Integer addFitnessScore;
    private Integer addCreditScore;
    private Integer customerRecordCount;
    private Date finalRecordTime;
    private Integer deleted;

    public Integer getCustomerRecordCount() {
        return customerRecordCount;
    }

    public void setCustomerRecordCount(Integer customerRecordCount) {
        this.customerRecordCount = customerRecordCount;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline ;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
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

    public Date getInGymTime() {
        return inGymTime;
    }

    public void setInGymTime(Date inGymTime) {
        this.inGymTime = inGymTime;
    }

    public Date getOutGymTime() {
        return outGymTime;
    }

    public void setOutGymTime(Date outGymTime) {
        this.outGymTime = outGymTime;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType == null ? null : exerciseType.trim();
    }

    public Integer getAddFitnessScore() {
        return addFitnessScore;
    }

    public void setAddFitnessScore(Integer addFitnessScore) {
        this.addFitnessScore = addFitnessScore;
    }

    public Integer getAddCreditScore() {
        return addCreditScore;
    }

    public void setAddCreditScore(Integer addCreditScore) {
        this.addCreditScore = addCreditScore;
    }

    public Date getFinalRecordTime() {
        return finalRecordTime;
    }

    public void setFinalRecordTime(Date finalRecordTime) {
        this.finalRecordTime = finalRecordTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "CustomerRecord{" +
                "recordId='" + recordId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", isOnline=" + isOnline +
                ", recordStatus=" + recordStatus +
                ", orderStartTime=" + orderStartTime +
                ", orderEndTime=" + orderEndTime +
                ", inGymTime=" + inGymTime +
                ", outGymTime=" + outGymTime +
                ", exerciseType='" + exerciseType + '\'' +
                ", addFitnessScore=" + addFitnessScore +
                ", addCreditScore=" + addCreditScore +
                ", customerRecordCount=" + customerRecordCount +
                ", finalRecordTime=" + finalRecordTime +
                ", deleted=" + deleted +
                '}';
    }
}
