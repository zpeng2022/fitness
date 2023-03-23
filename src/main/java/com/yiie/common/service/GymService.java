package com.yiie.common.service;

import com.yiie.entity.Gym;
import com.yiie.vo.data.GymIsClose;
import com.yiie.vo.data.GymOpenTimeVO;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;
import java.util.Map;

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

    List<Gym>  getByName(String name,String deptId);

    List<GymOpenTimeVO> getGymOT();

    List<String> selectAllName();

    List<GymIsClose> getIsClose();

    List<String> getAllTypes();

    List<Gym> getByDeptId(String deptId);

    List<String> selectAllNameById(String deptId);

    List<Gym> getAllGym(String name);

    List<GymOpenTimeVO> getGymOTByDeptId(String deptId);

    List<Gym> getAll();

    List<String> getTypesByDeptId(String deptId);

    List<Gym> getGymByDeptId(String deptId);
}
