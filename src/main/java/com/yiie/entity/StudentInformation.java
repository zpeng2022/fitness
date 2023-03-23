package com.yiie.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentInformation implements Serializable {
    private String studentStatus;//学籍号
    private String local;//县市区
    private String school;//学校名称
    private String gradeNum;//年级编号
    private String gradeName;//年级名称
    private String classNum;//班级编号
    private String className;//班级名称
    private String nationNum;//民族代码
    private String name;//姓名
    private Integer sex;//性别
    private String birth;//出生日期
    private String home;//家庭住址
}
