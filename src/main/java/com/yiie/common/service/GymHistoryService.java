package com.yiie.common.service;

import com.yiie.entity.GymHistory;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymHistoryService {
    PageVO<GymHistory> pageInfo(GymHistoryPageReqVO vo);
    void addGymHistories(GymHistoryAddReqVO vo);
    void updateGymHistoriesInfo(GymHistoryUpdateReqVO vo);
    void deletedGymHistories(List<String> userIds);
}
