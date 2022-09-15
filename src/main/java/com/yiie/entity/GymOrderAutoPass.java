package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO:
 * 创建机关单位的时候需要添加进去
 * 故, 需要修改那部分的代码
 */
public class GymOrderAutoPass implements Serializable {
    private String deptId;
    private Integer isAutoPass;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getIsAutoPass() {
        return isAutoPass;
    }

    public void setIsAutoPass(Integer isAutoPass) {
        this.isAutoPass = isAutoPass;
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
        return "GymOrderAutoPass{" +
                "deptId='" + deptId + '\'' +
                ", isAutoPass=" + isAutoPass +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
