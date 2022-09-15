package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO: 图片, gps, 还有开放的时间.
 * 场馆基本信息.
 */
public class Gym implements Serializable {
    private String gymId;
    /**
     * 所属的机关单位Id, 方便后面查询
     */
    private String deptId;
    private String gymName;

    // 以下三个字段不写入数据库, 返回给前端时再赋值.
    // 参考UserServiceImpl.
    private String deptName;
    private String monday;
    private String saturday;

    private String gymPhone;
    private Integer gymCapacity;
    /**
     * 有专门存放图片的路径
     * /src/pictures/gymId/gymPicturesPath.jpg
     * 存放的是gymPicturesPath.jpg/.png
     * 13时间戳 + 4位后缀(.jpg/.png等)
     * 场馆的描述图片的存放路径
     */
    private String gymPicturesPath;
    /**
     * 是否在节假日开放
     */
    private Integer openOnHoliday;
    private String gymTypes;
    /**
     * 文字描述场馆的地理位置
     */
    private String gymPosition;
    /**
     * 介绍场馆的描述信息
     */
    private String gymDetails;
    /**
     * 放在这里备用, 没有gps如何定位?
     */
    private String gymGps;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

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

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName == null ? null : gymName.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday == null ? null : monday.trim();
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday == null ? null : saturday.trim();
    }

    public String getGymPhone() {
        return gymPhone;
    }

    public void setGymPhone(String gymPhone) {
        this.gymPhone = gymPhone == null ? null : gymPhone.trim();
    }

    public Integer getGymCapacity() {
        return gymCapacity;
    }

    public void setGymCapacity(Integer gymCapacity) {
        this.gymCapacity = gymCapacity ;
    }

    public String getGymPicturesPath() {
        return gymPicturesPath;
    }

    public void setGymPicturesPath(String gymPicturesPath) {
        this.gymPicturesPath = gymPicturesPath == null ? null : gymPicturesPath.trim();
    }

    public Integer getOpenOnHoliday() {
        return openOnHoliday;
    }

    public void setOpenOnHoliday(Integer openOnHoliday) {
        this.openOnHoliday = openOnHoliday;
    }

    public String getGymTypes() {
        return gymTypes;
    }

    public void setGymTypes(String gymTypes) {
        this.gymTypes = gymTypes == null ? null : gymTypes.trim();;
    }

    public String getGymPosition() {
        return gymPosition;
    }

    public void setGymPosition(String gymPosition) {
        this.gymPosition = gymPosition == null ? null : gymPosition.trim();
    }

    public String getGymDetails() {
        return gymDetails;
    }

    public void setGymDetails(String gymDetails) {
        this.gymDetails = gymDetails == null ? null : gymDetails.trim();
    }

    public String getGymGps() {
        return gymGps;
    }

    public void setGymGps(String gymGps) {
        this.gymGps = gymGps;
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
        return "Gym{" +
                "gymId='" + gymId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", gymName='" + gymName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", monday='" + monday + '\'' +
                ", saturday='" + saturday + '\'' +
                ", gymPhone='" + gymPhone + '\'' +
                ", gymCapacity=" + gymCapacity +
                ", gymPicturesPath='" + gymPicturesPath + '\'' +
                ", openOnHoliday=" + openOnHoliday +
                ", gymTypes='" + gymTypes + '\'' +
                ", gymPosition='" + gymPosition + '\'' +
                ", gymDetails='" + gymDetails + '\'' +
                ", gymGps='" + gymGps + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
