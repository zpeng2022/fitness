package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CustomerRecordPageReqVO {
    public CustomerRecordPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "用户记录id")
    private String recordId;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "customerId不可为空.")
    private String customerId;

    @ApiModelProperty(value = "是否是线上用户")
    private Integer isOnline;

    @ApiModelProperty(value = "记录的状态")
    private Integer recordStatus;

    @ApiModelProperty(value = "线上用户预约的开始时间")
    private Date orderStartTime;

    @ApiModelProperty(value = "线上用户预约的结束时间")
    private Date orderEndTime;

    @ApiModelProperty(value = "用户进入场馆的时间")
    private Date inGymTime;

    @ApiModelProperty(value = "用户出去场馆的时间")
    private Date outGymTime;

    @ApiModelProperty(value = "运动类型")
    private String exerciseType;

    @ApiModelProperty(value = "给健身积分添加的分数")
    private Integer addFitnessScore;

    @ApiModelProperty(value = "信用积分添加的分数")
    private Integer addCreditScore;

    @ApiModelProperty(value = "最后一条记录的时间")
    private Date finalRecordTime;

    private Integer deleted;
    // deleted...
}
