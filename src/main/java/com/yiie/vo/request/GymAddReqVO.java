package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class GymAddReqVO {
    @ApiModelProperty(value = "场地Id")
    private String gymId;

    @ApiModelProperty(value = "机关单位Id")
    private String deptId;

    @NotBlank(message = "场地名称不能为空")
    @ApiModelProperty(value = "场地名称")
    private String gymName;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "周一至周五开放时间")
    private String monday;

    @ApiModelProperty(value = "周六周日开放时间, 不开放默认为空")
    private String saturday;

    @ApiModelProperty(value = "场地电话")
    private String gymPhone;

    @ApiModelProperty(value = "场地最大容量")
    private Integer gymCapacity;

    @ApiModelProperty(value = "场地图片地址")
    private String gymPicturesPath;

    @ApiModelProperty(value = "节假日是否开放")
    private Integer openOnHoliday;

    @ApiModelProperty(value = "场地类型")
    private String gymTypes;

    @ApiModelProperty(value = "场地地址")
    private String gymPosition;

    @ApiModelProperty(value = "场地具体信息")
    private String gymDetails;

    @ApiModelProperty(value = "场地Gps")
    private String gymGps;

    @ApiModelProperty(value = "场地创建时间")
    private String createTime;

    @ApiModelProperty(value = "场地更新时间")
    private String updateTime;

    @ApiModelProperty(value = "是否删除")
    private Integer deleted;
}
