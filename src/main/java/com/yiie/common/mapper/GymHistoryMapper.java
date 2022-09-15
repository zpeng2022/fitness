package com.yiie.common.mapper;

import com.yiie.entity.GymCustomTags;
import com.yiie.entity.GymHistory;
import com.yiie.vo.request.GymHistoryPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymHistoryMapper {
    List<GymHistory> getAllGymHistoriesByDeptID(String deptId);
    List<GymHistory> selectAllGymHistoriesByDeptID(GymHistoryPageReqVO vo);
    GymHistory selectByPrimaryKey(String tagId);
    int insertSelective(GymHistory gymHistory);
    int updateByPrimaryKeySelective(GymHistory gymHistory);
    int deletedGymHistories(@Param("sysUser") GymHistory sysUser, @Param("list") List<String> list);
}