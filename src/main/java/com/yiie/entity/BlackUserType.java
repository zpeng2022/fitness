package com.yiie.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlackUserType {
    private String typeId;
    private String deptId;
    private String typeName;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
    private Dept dept;
}
