package com.yiie.vo.request;

import com.yiie.entity.BlackUserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlackUserPageReqVO {
    public BlackUserPageReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "身份证号码")
    private String identityCard;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "人员类型信息")
    private String typeInfo;

    @ApiModelProperty(value = "导入时间")
    private String importTime1;

    @ApiModelProperty(value = "导入时间")
    private String importTime2;

    @ApiModelProperty(value = "更新时间")
    private Integer updateTime;

    @ApiModelProperty(value = "性别(1.男 2.女 3.保密 ")
    private Integer sex;

    @ApiModelProperty(value = "性别(0.已删除 1.未删除 ")
    private Integer deleted;


}
