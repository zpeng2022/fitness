package com.yiie.common.mapper;

import com.yiie.entity.Gym;
import com.yiie.entity.GymCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymCountMapper {
    List<GymCount> getAllGymCountsByNameByDeptID(String deptId);
    GymCount selectByPrimaryKey(String gymId);
    int insertSelective(GymCount gymcount);
    int updateByPrimaryKeySelective(GymCount gymcount);
    int deletedGymCounts(@Param("sysUser") GymCount sysUser, @Param("list") List<String> list);

}
