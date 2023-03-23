package com.yiie.common.service;

import com.yiie.entity.GymHistory;
import com.yiie.vo.data.*;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.Date;
import java.util.List;

public interface GymHistoryService {
    PageVO<GymHistory> pageInfo(GymHistoryPageReqVO vo);
    void addGymHistories(GymHistoryAddReqVO vo);
    void updateGymHistoriesInfo(GymHistoryUpdateReqVO vo);
    void deletedGymHistories(List<String> userIds);

    List<SportAndValue> getTypeAndValue(String name);

    List<String> getIdentity(String gymId);

    List<DateSpan> getOrderDateSpan(String gymId);

    List<PeopleSportTime> getPeopleSportToday(String gymId,Date S,Date E);

    List<GymPeopleMonth> getPeopleNumMonth(Date monthAgo, Date nowTime);
    List<OnlineNum> getIsOnlineNum(String gymId);

    List<String> getTypeAndValueByIDCard(String customerIdentityCard);

    List<PeopleSportTime> getUserSportTimes(String userIdentityCard);

    List<PeopleSportTime> getPeopleSportTimes(String gymId);

    List<GymPeopleMonth> getPeopleNumMonthByDeptId(Date monthAgo, Date todayTime, String deptId);

    int getCustomerSportDay(String customerIdentityCard);

    int getCustomerGymNum(String customerIdentityCard);

    List<PeopleSportTime> getPeopleSportTimesByCustomerId(String customer_identity_card,int year);

    List<PeopleSportTime> getPeopleSportTimesByCustomerId2(String customer_identity_card);
}
