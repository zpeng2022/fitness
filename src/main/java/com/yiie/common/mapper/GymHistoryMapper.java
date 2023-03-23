package com.yiie.common.mapper;

import com.yiie.entity.GymCustomTags;
import com.yiie.entity.GymHistory;
import com.yiie.vo.data.*;
import com.yiie.vo.request.GymHistoryPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.yiie.vo.data.DateSpan;
import com.yiie.vo.data.GymPeopleMonth;
import com.yiie.vo.data.PeopleSportTime;
import com.yiie.vo.data.SportAndValue;
import javax.xml.crypto.Data;
import java.util.Date;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface GymHistoryMapper {
    List<GymHistory> getAllGymHistoriesByDeptID(String deptId);
    List<GymHistory> selectAllGymHistoriesByDeptID(GymHistoryPageReqVO vo);
    List<GymHistory> selectAllGymHistoriesByDeptID2(GymHistoryPageReqVO vo);
    GymHistory selectByPrimaryKey(String tagId);
    int insertSelective(GymHistory gymHistory);
    int updateByPrimaryKeySelective(GymHistory gymHistory);
    // TODO... test...
    List<GymHistory> getCustomerHistoryWithOnlineStateAndTime(@Param("customerIdentityCard") String customerIdentityCard, @Param("isOnlineUser") Integer isOnlineUser, @Param("createTime") Date createTime);
    int deletedGymHistories(@Param("sysUser") GymHistory sysUser, @Param("list") List<String> list);

    List<SportAndValue> getTypeAndValue(String name);

    List<String> getIdentity(String gymId);

    List<DateSpan> getOrderDateSpan(String gymId);

    List<PeopleSportTime> getPeopleSportTimes(String gymId);

    List<GymPeopleMonth> getPeopleNumMonth(Date monthAgo, Date nowTime);

    List<OnlineNum> getIsOnlineNum(String gymId);

    List<String> getTypeAndValueByIDCard(String customerIdentityCard);

    List<PeopleSportTime> getUserSportTimes(String userIdentityCard);

    int checkIsComing(String s, Date orderStart, Date orderEnd);

    List<PeopleSportTime> getPeopleSportToday(String gymId, Date s, Date e);

    List<GymPeopleMonth> getPeopleNumMonthByDeptId(Date monthAgo, Date todayTime, String deptId);

    int getCustomerSportDay(String customerIdentityCard);

    int getCustomerGymNum(String customerIdentityCard);

    List<PeopleSportTime> getPeopleSportTimesByCustomerId(String customer_identity_card,int year);

    List<PeopleSportTime> getPeopleSportTimesByCustomerId2(String customer_identity_card);
}
