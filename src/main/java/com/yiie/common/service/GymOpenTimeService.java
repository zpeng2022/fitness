package com.yiie.common.service;

import com.yiie.vo.data.GymCloseTime;

import java.util.Date;
import java.util.List;

public interface GymOpenTimeService {
    List<GymCloseTime> getGymCT(String gymId,Date today, Date fiveDaysAgo);
}
