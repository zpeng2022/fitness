package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 暂时的设定是 一个机关单位 设置 customTag
 * 反正可以通用.
 */
public class GymCustomTags implements Serializable {
    private String tagId;
    private String deptId;
    /**
     * tagCount表示是deptId的第几个tag.
     */
    private Integer tagCount;
    /**
     * tag的具体内容.
     */
    private String tagContent;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public Integer getTagCount() {
        return tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent == null ? null : tagContent.trim();
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
        return "GymCustomTags{" +
                "tagId='" + tagId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", tagCount=" + tagCount +
                ", tagContent='" + tagContent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
