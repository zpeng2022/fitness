package com.yiie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 统计当前gym的人数.
 * 历史总人数
 * 注意更新时间时, 按照最后一个来更新时间...
 */
public class GymCount implements Serializable {
    private String gymId;
    private String deptId;
    /**
     * currentCount 每天清零
     */
    private Integer currentCount;
    private Integer historyCount;
    /**
     * 记录currentCount统计的是哪一天的数据.
     */
    private Date currentDate;
    private Integer deleted;
}
