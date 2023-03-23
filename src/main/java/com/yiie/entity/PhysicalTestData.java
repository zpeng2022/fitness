package com.yiie.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PhysicalTestData implements Serializable {
    private String id;//id
    private String identitycard;//身份证号码
    private Integer exempt;//免测
    private String height;//身高
    private String weight;//体重
    private String vitalCapacity;//肺活量
    private String runFifty;//50米跑
    private String settingForward;//坐位体前屈
    private String skippingRope;//跳绳
    private String leftVision;//左眼视力
    private String rightVision;//右眼视力
    private String leftError;//左眼屈光不正
    private String rightError;//右眼屈光不正
    private String leftMirror;//左眼串镜
    private String rightMirror;//右眼串镜
    private String abdominalCurl;//仰卧起坐
    private String runBack;//50米*8往返跑
    private Customer customer;//用户参数
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}
