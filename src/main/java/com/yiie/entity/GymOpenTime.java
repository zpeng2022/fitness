package com.yiie.entity;

import java.io.Serializable;

/**
 * 场馆的开放时间..
 * 查询所有时间之后, 按照gymId排序, 再按照whichday排序.
 */
public class GymOpenTime implements Serializable {
    private String openTimeId;
    private String gymId;
    private String deptId;
    /**
     * 1  monday
     * 2  tuesday
     * 3  wednesday
     * ...
     * 7  sunday
     */
    private Integer whichDay;
    private String openTime;
    private String closeTime;
    private Integer deleted;
}
