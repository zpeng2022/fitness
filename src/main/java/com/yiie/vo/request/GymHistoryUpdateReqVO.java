package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class GymHistoryUpdateReqVO {
    @NotBlank(message = "历史Id不能为空")
    @ApiModelProperty(value = "历史Id")
    private String historyId;

    @NotBlank(message = "场地Id不能为空")
    @ApiModelProperty(value = "场地Id")
    private String gymId;

    /**
     * 所属的机关单位Id, 显示的时候需要
     */
    @NotBlank(message = "部门Id不能为空")
    @ApiModelProperty(value = "部门Id")
    private String deptId;

    @ApiModelProperty(value = "用户名称")
    private String customerName;

    @ApiModelProperty(value = "用户手机号码")
    private String customerPhone;

    @NotBlank(message = "用户身份证号码不能为空")
    @ApiModelProperty(value = "用户身份证号码")
    private String customerIdentityCard;
    /**
     * 获取机关单位id.
     * 查询禁入名单.
     * 0 非禁入名单用户
     * 1 禁入名单用户
     */
    @ApiModelProperty(value = "是否是禁入人员")
    private Integer isBlackUser;
    /**
     * 查询是否为 当天 的预约用户.
     * orderTime, exerciseType也是
     * 通过order来查询.
     * 线下的exerciseType和orderTime
     * 按照默认设置(空?)
     * GymOrderDetail..
     * 0 线下人员
     * 1 线上预约人员
     */
    @ApiModelProperty(value = "是否是在线人员")
    private Integer isOnlineUser;
    /**
     * orderTime是预约要过去的时间
     */
    @ApiModelProperty(value = "预约的开始时间")
    private Date orderStartTime;

    @ApiModelProperty(value = "预约的结束时间")
    private Date orderEndTime;

    @ApiModelProperty(value = "锻炼类型")
    private String exerciseType;
    /**
     * 进出场馆的时间
     */
    @ApiModelProperty(value = "进入场馆的时间")
    private Date inTime;

    @ApiModelProperty(value = "出去场馆的时间")
    private Date outTime;

    @ApiModelProperty(value = "记录创建的时间")
    private Date createTime;

    @ApiModelProperty(value = "记录更新的时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除记录")
    private Integer deleted;
}
