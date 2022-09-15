package com.yiie.common.service;

import com.yiie.entity.Gym;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymService {
    PageVO<Gym> pageInfo(GymPageReqVO vo);
    void addGym(GymAddReqVO vo);
    void updateGymInfo(GymUpdateReqVO vo);
    void deletedGyms(List<String> userIds);
}