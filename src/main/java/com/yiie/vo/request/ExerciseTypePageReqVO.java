package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExerciseTypePageReqVO {

    public ExerciseTypePageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "exerciseId")
    private Integer typeId;

    @ApiModelProperty(value = "名称")
    private String typeName;

    @NotBlank(message = "描述")
    @ApiModelProperty(value = "场地名称")
    private String descripton;

    @ApiModelProperty(value = "导入时间")
    private String startTime;

    @ApiModelProperty(value = "导入时间")
    private String endTime;
    @ApiModelProperty(value = "机关单位")
    private String deptId;

    @ApiModelProperty(value = "deleted")
    private Integer deleted;

}
