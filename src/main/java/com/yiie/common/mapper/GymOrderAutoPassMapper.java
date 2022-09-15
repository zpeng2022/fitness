package com.yiie.common.mapper;

import com.yiie.entity.GymOrderAutoPass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymOrderAutoPassMapper {
    GymOrderAutoPass getGymOrderAutoPassByDeptID(String deptId);
    GymOrderAutoPass selectByPrimaryKey(String deptId);
    int insertSelective(GymOrderAutoPass gymOrderAutoPass);
    int updateByPrimaryKeySelective(GymOrderAutoPass gymOrderAutoPass);
    int deletedGymOrderAutoPass(@Param("sysUser") GymOrderAutoPass sysUser, @Param("list") List<String> list);
}
