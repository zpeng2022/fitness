package com.yiie.common.service;

import com.yiie.entity.GymHistory;
import com.yiie.vo.data.*;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import javax.xml.crypto.Data;
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

    List<PeopleSportTime> getPeopleSportTimes(String gymId);

    List<GymPeopleMonth> getPeopleNumMonth(Date monthAgo, Date nowTime);
}
