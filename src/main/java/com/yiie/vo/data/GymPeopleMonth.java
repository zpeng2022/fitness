package com.yiie.vo.data;

import lombok.Data;

@Data
public class GymPeopleMonth {
    private String gymId;
    private String gymName;
    private Integer num;

    public GymPeopleMonth() {
    }

    public GymPeopleMonth(String gymId, String gymName, Integer num) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.num = num;
    }
}
