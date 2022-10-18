package com.yiie.vo.data;

import lombok.Data;

import java.util.Date;

@Data
public class PeopleSportTime {
    private String idcard;
    private String name;
    private Date start;
    private Date end;

    public PeopleSportTime() {
    }
}
