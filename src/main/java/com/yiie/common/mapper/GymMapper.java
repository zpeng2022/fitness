package com.yiie.common.mapper;

import com.yiie.entity.Gym;
import com.yiie.vo.request.GymPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymMapper {
    List<Gym> getAllGymsByDeptID(String deptId);
    List<Gym> selectAllGymsByDeptID(GymPageReqVO vo);
    List<Gym> h5GetAllGyms(GymPageReqVO vo);
    Gym selectByPrimaryKey(String gymId);
    int insertSelective(Gym gym);
    int updateByPrimaryKeySelective(Gym gym);
    int deletedGyms(@Param("sysUser") Gym sysUser, @Param("list") List<String> list);
}
