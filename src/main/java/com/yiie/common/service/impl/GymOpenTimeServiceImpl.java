package com.yiie.common.service.impl;

import com.yiie.common.mapper.GymOpenTimeMapper;
import com.yiie.common.service.GymOpenTimeService;
import com.yiie.vo.data.GymCloseTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GymOpenTimeServiceImpl implements GymOpenTimeService {
    @Autowired
    GymOpenTimeMapper gymOpenTimeMapper;

    @Override
    public List<GymCloseTime> getGymCT(String gymId,Date today, Date fiveDaysAgo) {
        return gymOpenTimeMapper.getGymCT(gymId,today,fiveDaysAgo);
    }
}
