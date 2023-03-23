package com.yiie.common.service;

import com.yiie.entity.BlackUserType;
import com.yiie.vo.request.BlackTypeAddReqVO;
import com.yiie.vo.request.BlackTypePageReqVO;
import com.yiie.vo.request.BlackTypeUpdateReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface BlackUserTypeService {

    PageVO<BlackUserType> pageInfo(BlackTypePageReqVO vo);

    void deletedBlackUserType(List<String> userIds, String operationId);

    void addBlackUserType(BlackTypeAddReqVO vo);

    void updateBlackUserType(BlackTypeUpdateReqVO vo, String operationId);

    List<BlackUserType> getByDeptId(BlackUserType b);
}
