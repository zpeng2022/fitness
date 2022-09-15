package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表单
 */
public class GymComments implements Serializable {
    private String commentId;
    /**
     * 显示的时候需要deptId, gymId.
     */
    private String gymId;
    private String deptId;
    private String commentCustomerId;
    private String commentCustomerPhone;
    private String commentCustomerIdentityCard;
    private Integer commentStars;
    /**
     * separate with #
     */
    private String commentTags;
    /**
     * 最新的按照最新的Tag写入到.
     * 不要每次都查询一边Tag对应的内容,
     * 从而把计算资源放在了不重要的地方
     * separate with #
     */
    private String commentTagContent;
    private String commentDetail;
    /**
     * 机关单位的 回复信息.
     */
    private String commentAnswer;
    private String commentCustomerName;
    /**
     * 0 表示未读
     * 1 表示已读
     */
    private Integer commentStatus;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;

    public String getCommentTagContent() {
        return commentTagContent;
    }

    public String getCommentCustomerIdentityCard() {
        return commentCustomerIdentityCard;
    }

    public void setCommentCustomerIdentityCard(String commentCustomerIdentityCard) {
        this.commentCustomerIdentityCard = commentCustomerIdentityCard == null ? null : commentCustomerIdentityCard.trim();
    }

    public void setCommentTagContent(String commentTagContent) {
        this.commentTagContent = commentTagContent == null ? null : commentTagContent.trim();
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
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

    public String getCommentCustomerId() {
        return commentCustomerId;
    }

    public void setCommentCustomerId(String commentCustomerId) {
        this.commentCustomerId = commentCustomerId == null ? null : commentCustomerId.trim();
    }

    public String getCommentCustomerPhone() {
        return commentCustomerPhone;
    }

    public void setCommentCustomerPhone(String commentCustomerPhone) {
        this.commentCustomerPhone = commentCustomerPhone == null ? null : commentCustomerPhone.trim();
    }

    public Integer getCommentStars() {
        return commentStars;
    }

    public void setCommentStars(Integer commentStars) {
        this.commentStars = commentStars;
    }

    public String getCommentTags() {
        return commentTags;
    }

    public void setCommentTags(String commentTags) {
        this.commentTags = commentTags == null ? null : commentTags.trim();
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail == null ? null : commentDetail.trim();
    }

    public String getCommentAnswer() {
        return commentAnswer;
    }

    public void setCommentAnswer(String commentAnswer) {
        this.commentAnswer = commentAnswer == null ? null : commentAnswer.trim();
    }

    public String getCommentCustomerName() {
        return commentCustomerName;
    }

    public void setCommentCustomerName(String commentCustomerName) {
        this.commentCustomerName = commentCustomerName == null ? null : commentCustomerName.trim();
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
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
        return "GymComments{" +
                "commentId='" + commentId + '\'' +
                ", gymId='" + gymId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", commentCustomerId='" + commentCustomerId + '\'' +
                ", commentCustomerPhone='" + commentCustomerPhone + '\'' +
                ", commentCustomerIdentityCard='" + commentCustomerIdentityCard + '\'' +
                ", commentStars=" + commentStars +
                ", commentTags='" + commentTags + '\'' +
                ", commentTagContent='" + commentTagContent + '\'' +
                ", commentDetail='" + commentDetail + '\'' +
                ", commentAnswer='" + commentAnswer + '\'' +
                ", commentCustomerName='" + commentCustomerName + '\'' +
                ", commentStatus=" + commentStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}
