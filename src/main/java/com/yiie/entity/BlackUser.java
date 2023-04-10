package com.yiie.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class BlackUser implements Serializable {
    private String id;
    private String identityCard;
    private String username;
    /**
     * to distinguish different department's black users.
     */
    private String deptID;
    private String phone;
    private String typeInfo;
    private Date importTime;
    private Date updateTime;
    private Integer sex;
    /**
     *  if deleted == 0, and we will delete it.
     */
    private Integer deleted;

    @ApiModelProperty("机关单位")
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo == null ? null : typeInfo.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "BlackUser{" +
                "id='" + id + '\'' +
                ", deptID='" + deptID + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", typeInfo='" + typeInfo + '\'' +
                ", importTime=" + importTime +
                ", updateTime=" + updateTime +
                ", sex=" + sex +
                ", deleted=" + deleted +
                '}';
    }
}
