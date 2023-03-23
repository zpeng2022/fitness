package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GymPageReqVO {
    public GymPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "场地Id")
    private String gymId;

    @ApiModelProperty(value = "机关单位Id")
    private String deptId;

    @ApiModelProperty(value = "场地名称")
    private String gymName;

    // 以下三个字段不写入数据库, 返回给前端时再赋值.
    // 参考UserServiceImpl.
    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "周一至周五开放时间")
    private String monday;

    @ApiModelProperty(value = "周六周日开放时间, 不开放默认为空")
    private String saturday;

    @ApiModelProperty(value = "运动类型")
    private String exerciseType;

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
    @ApiModelProperty(value = "场地创建时间")
    private String startTime;

    @ApiModelProperty(value = "场地创建时间")
    private String endTime;

    @ApiModelProperty(value = "场地更新时间")
    private String updateTime;

    @ApiModelProperty(value = "场地是否删除")
    private Integer deleted;

    @ApiModelProperty(value = "是否已经闭馆：1-闭，0-未闭")
    private Integer isClose;

    @ApiModelProperty(value = "是否已经闭馆：1-闭，0-未闭")
    private List<String> gymPicturesPathList;
}
