package com.yiie.common.service;

import com.yiie.entity.PhysicalTestData;
import com.yiie.vo.request.BlackUserUpdateReqVO;
import com.yiie.vo.request.PhysicalTestReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface PhysicalTestService {
    PageVO<PhysicalTestData> pageInfo(PhysicalTestReqVO vo);

    void deletedPhysical(List<String> userIds, String operationId);

    void addPhysical(PhysicalTestData vo);

    void updatePhysical(PhysicalTestData vo);

    PhysicalTestData getByCustomerId(String userId);
}
