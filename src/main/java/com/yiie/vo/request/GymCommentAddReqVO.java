package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class GymCommentAddReqVO {
    @ApiModelProperty(value = "评论id")
    private String commentId;
    /**
     * 显示的时候需要deptId, gymId.
     */
    @NotBlank(message = "场地Id不能为空")
    @ApiModelProperty(value = "场地Id")
    private String gymId;

    @NotBlank(message = "机关单位Id不能为空")
    @ApiModelProperty(value = "机关单位Id")
    private String deptId;

    @NotBlank(message = "用户Id不能为空")
    @ApiModelProperty(value = "用户Id")
    private String commentCustomerId;

    @ApiModelProperty(value = "用户电话")
    private String commentCustomerPhone;

    @NotBlank(message = "用户身份证号码不能为空")
    @ApiModelProperty(value = "用户身份证号码")
    private String commentCustomerIdentityCard;

    @ApiModelProperty(value = "用户评论星号")
    private Integer commentStars;
    /**
     * separate with #
     */
    @ApiModelProperty(value = "用户所用的Tag, 用#分隔")
    private String commentTags;

    /**
     * show the commentTags' Content to the backend user.
     */
    @ApiModelProperty(value = "用户所用的Tag的内容, 分隔")
    private String commentTagContent;

    @NotBlank(message = "tag的内容不能为空")
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
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;
}
