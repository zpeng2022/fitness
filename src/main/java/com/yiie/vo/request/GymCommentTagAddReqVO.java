package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class GymCommentTagAddReqVO {

    @ApiModelProperty(value = "tagId")
    private String tagId;

    @ApiModelProperty(value = "deptId")
    private String deptId;

    @ApiModelProperty(value = "第几个tag")
    private Integer tagCount;

    @NotBlank(message = "tag的内容不能为空")
    @ApiModelProperty(value = "tag的内容")
    private String tagContent;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "0表示删除, 1表示不删除")
    private Integer deleted;
}
