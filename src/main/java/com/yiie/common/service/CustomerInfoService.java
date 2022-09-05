package com.yiie.common.service;

import com.yiie.entity.CustomerInfo;
import com.yiie.vo.request.CustomerInfoAddReqVO;
import com.yiie.vo.request.CustomerInfoPageReqVO;
import com.yiie.vo.request.CustomerInfoUpdateReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface CustomerInfoService {
    PageVO<CustomerInfo> pageInfo(CustomerInfoPageReqVO vo);
    void updateCustomerInfo(CustomerInfoUpdateReqVO vo, String operationId);
    void deletedCustomersInfo(List<String> userIds, String operationId);
}
