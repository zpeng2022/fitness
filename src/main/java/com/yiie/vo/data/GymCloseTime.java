package com.yiie.vo.data;

import lombok.Data;

import java.util.Date;

@Data
public class GymCloseTime {
    private String gymId;
    private Date whichDay;
    private String closeTime;
}
