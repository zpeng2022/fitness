package com.yiie.vo.request;

import com.yiie.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PhysicalTestReqVO {
    public PhysicalTestReqVO(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    private String identitycard;//身份证号码
    private Integer exempt;//免测
    private String height1;//身高
    private String height2;//身高
    private String weight1;//体重
    private String weight2;//体重
    private String vitalCapacity1;//肺活量
    private String vitalCapacity2;//肺活量
    private String runFifty1;//50米跑
    private String runFifty2;//50米跑
    private String settingForward1;//坐位体前屈
    private String settingForward2;//坐位体前屈
    private String skippingRope1;//跳绳
    private String skippingRope2;//跳绳
    private String leftVision1;//左眼视力
    private String leftVision2;//左眼视力
    private String rightVision1;//右眼视力
    private String rightVision2;//右眼视力
    private String leftError1;//左眼屈光不正
    private String leftError2;//左眼屈光不正
    private String rightError1;//右眼屈光不正
    private String rightError2;//右眼屈光不正
    private String leftMirror1;//左眼串镜
    private String leftMirror2;//左眼串镜
    private String rightMirror1;//右眼串镜
    private String rightMirror2;//右眼串镜
    private String abdominalCurl1;//仰卧起坐
    private String abdominalCurl2;//仰卧起坐
    private String runBack1;//50米*8往返跑
    private String runBack2;//50米*8往返跑
    private Customer customer;//用户其他参数
    private Date createTime;
    private String updateTime1;
    private String updateTime2;
    private Integer deleted;
}
