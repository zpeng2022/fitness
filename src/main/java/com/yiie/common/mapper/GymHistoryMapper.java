package com.yiie.common.mapper;

import com.yiie.entity.GymHistory;
import com.yiie.vo.data.DateSpan;
import com.yiie.vo.data.GymPeopleMonth;
import com.yiie.vo.request.GymHistoryPageReqVO;
import com.yiie.vo.data.PeopleSportTime;
import com.yiie.vo.data.SportAndValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
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

    List<SportAndValue> getTypeAndValue(String name);

    List<String> getIdentity(String gymId);

    List<DateSpan> getOrderDateSpan(String gymId);

    List<PeopleSportTime> getPeopleSportTimes(String gymId);

    List<GymPeopleMonth> getPeopleNumMonth(Date monthAgo, Date nowTime);
}
