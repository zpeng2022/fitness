package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ExerciseTypeUpdateReqVO {

    @ApiModelProperty(value = "typeId")
//    @NotBlank(message = "typeId不能为空")
    private Integer typeId;

    @ApiModelProperty(value = "名称")
    private String typeName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除")
    private Integer deleted;
}
