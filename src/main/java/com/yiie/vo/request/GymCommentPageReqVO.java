package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class GymCommentPageReqVO {
    public GymCommentPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "评论id")
    private String commentId;
    /**
     * 显示的时候需要deptId, gymId.
     */
    @ApiModelProperty(value = "场地Id")
    private String gymId;

    @ApiModelProperty(value = "机关单位Id")
    private String deptId;

    @ApiModelProperty(value = "用户Id")
    private String commentCustomerId;

    @ApiModelProperty(value = "用户电话")
    private String commentCustomerPhone;

    @ApiModelProperty(value = "用户身份证号码")
    private String commentCustomerIdentityCard;

    @ApiModelProperty(value = "用户评论星号")
    private Integer commentStars;
    /**
     * separate with #
     */
    @ApiModelProperty(value = "用户所用的Tag, 用#分隔")
    private String commentTags;

    @ApiModelProperty(value = "用户所用的Tag的内容, 分隔")
    private String commentTagContent;

    @ApiModelProperty(value = "用户评论的内容")
    private String commentDetail;
    /**
     * 机关单位的 回复信息.
     */
    @ApiModelProperty(value = "机关单位的回复")
    private String commentAnswer;

    @ApiModelProperty(value = "用户的名字")
    private String commentCustomerName;
    /**
     * 0 表示未读
     * 1 表示已读
     */
    @ApiModelProperty(value = "评论的状态")
    private Integer commentStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;
}
