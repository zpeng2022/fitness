package com.yiie.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Article implements Serializable {
    @ApiModelProperty("主键:ID")
    private String id;

    @ApiModelProperty("标题")
    @NotBlank(message = "文章标题不能为空")
//    @Length(max = 100, message = "文章标题长度不能超过100")
    private String title;

    @ApiModelProperty("摘要")
    private String summary;

    @ApiModelProperty("HTML内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty("markdown内容")
    private String textContent;

    @ApiModelProperty("封面URL")
    private String cover;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("收藏量")
    private Integer collections;

    @ApiModelProperty("点赞量")
    private Integer likes;

    @ApiModelProperty("评论量")
    private Integer comments;

    @ApiModelProperty("是否开启评论")
    private Boolean commentable;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty("是否推荐")
    private Boolean recommend;

    @ApiModelProperty("作者ID")
    private String authorId;

    @ApiModelProperty("部门id")
    private String deptId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("状态[0:删除, 1:正常]")
    private Integer deleted;

    @ApiModelProperty("发布者")
    private User author;

    @ApiModelProperty("机关单位")
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
