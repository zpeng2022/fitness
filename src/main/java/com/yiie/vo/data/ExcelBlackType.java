package com.yiie.vo.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelBlackType {
    @ExcelProperty(value = "姓名",index = 0)
    private String username;

    @ExcelProperty(value = "身份证",index = 1)
    private String identityCard;

    @ExcelProperty(value = "黑名单类型ID",index = 2)
    private String typeInfo;

    @ExcelProperty(value = "手机号",index = 3)
    private String phone;

    @ExcelProperty(value = "性别",index = 4)
    private String sex;

    @ExcelProperty(value = "黑名单类型ID",index = 6)
    private String typeId;

    @ExcelProperty(value = "黑名单类型名称",index = 7)
    private String typeName;
}
