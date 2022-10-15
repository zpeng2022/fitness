package com.yiie.vo.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelBlackInfoVO {
    //设置Excel表头名称
    //value用于写入excel时充当表头名称
    //index用于读取excel时标注列数
    @ExcelProperty(value = "username",index = 0)
    private String username;

    @ExcelProperty(value = "identityCard",index = 1)
    private String identityCard;

    @ExcelProperty(value = "typeInfo",index = 2)
    private String typeInfo;

    @ExcelProperty(value = "phone",index = 3)
    private String phone;

    @ExcelProperty(value = "sex",index = 4)
    private String sex;
}
