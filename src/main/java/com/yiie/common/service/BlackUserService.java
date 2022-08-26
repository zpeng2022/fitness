package com.yiie.common.service;

import com.yiie.entity.BlackUser;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface BlackUserService {
    PageVO<BlackUser> pageInfo(BlackUserPageReqVO vo);
    void addBlackUser(BlackUserAddReqVo vo);
    void updateBlackUserInfo(BlackUserUpdateReqVO vo, String operationId);
    void deletedBlackUsers(List<String> userIds, String operationId);
}
