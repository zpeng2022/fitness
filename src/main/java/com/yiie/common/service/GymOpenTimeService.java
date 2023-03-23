package com.yiie.common.service;

import com.yiie.entity.GymOpenTime;
import com.yiie.vo.data.GymCloseTime;

import java.util.Date;
import java.util.List;

public interface GymOpenTimeService {
    List<GymCloseTime> getGymCT(String gymId,Date today, Date fiveDaysAgo);

    void addCloseTime(GymOpenTime gymOpenTime);

    GymOpenTime getByIdAndDay(String s, Date today);

    List<GymCloseTime> getGymCT2(String sG, Date today, Date fiveDaysAgo, String deptId);
}
