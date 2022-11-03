package com.yiie.common.service;

import com.yiie.entity.Gym;
import com.yiie.vo.data.GymOpenTimeVO;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymService {
    PageVO<Gym> h5PageInfo(GymPageReqVO vo);
    PageVO<Gym> h5GymSearch(GymSearchNamePageReqVO vo);
    PageVO<Gym> pageInfo(GymPageReqVO vo);
    void addGym(GymAddReqVO vo);
    Gym getGymById(String Id);
    void updateGymInfo(GymUpdateReqVO vo);
    void deletedGyms(List<String> userIds);

    Gym getById(String gymId);

    void autoPassBydeptId(String deptId);

    List<Gym>  getByName(String name);

    List<GymOpenTimeVO> getGymOT();

    List<String> selectAllName();
}
