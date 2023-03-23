package com.yiie.vo.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PhysicalInfoVo {
    @ExcelProperty(value = "学籍号",index = 6)
    private String studentStatus;//学籍号
    @ExcelProperty(value = "免测",index = 8)
    private int exempt;//免测
    @ExcelProperty(value = "身高",index = 13)
    private String height;//身高
    @ExcelProperty(value = "体重",index = 14)
    private String weight;//体重
    @ExcelProperty(value = "肺活量",index = 15)
    private String vitalCapacity;//肺活量
    @ExcelProperty(value = "50米跑",index = 16)
    private String runFifty;//50米跑
    @ExcelProperty(value = "坐位体前屈",index = 17)
    private String settingForward;//坐位体前屈
    @ExcelProperty(value = "一分钟跳绳",index = 18)
    private String skippingRope;//跳绳
    @ExcelProperty(value = "左眼裸眼视力",index = 19)
    private String leftVision;//左眼视力
    @ExcelProperty(value = "右眼裸眼视力",index = 20)
    private String rightVision;//右眼视力
    @ExcelProperty(value = "左眼屈光不正",index = 21)
    private String leftError;//左眼屈光不正
    @ExcelProperty(value = "右眼屈光不正",index = 22)
    private String rightError;//右眼屈光不正
    @ExcelProperty(value = "左眼串镜",index = 23)
    private String leftMirror;//左眼串镜
    @ExcelProperty(value = "右眼串镜",index = 24)
    private String rightMirror;//右眼串镜
    @ExcelProperty(value = "一分钟仰卧起坐",index = 25)
    private String abdominalCurl;//仰卧起坐
    @ExcelProperty(value = "50米×8往返跑",index = 26)
    private String runBack;//50米*8往返跑
}
