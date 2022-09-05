package com.yiie.common.service;

import com.yiie.entity.CustomerContact;
import com.yiie.vo.request.CustomerContactAddReqVO;
import com.yiie.vo.request.CustomerContactPageReqVO;
import com.yiie.vo.request.CustomerContactUpdateReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface CustomerContactService {
    PageVO<CustomerContact> pageInfo(CustomerContactPageReqVO vo);
    void addCustomerContact(CustomerContactAddReqVO vo);
    void updateCustomerContact(CustomerContactUpdateReqVO vo, String operationId);
    void deletedCustomerContacts(List<String> userIds, String operationId);
}
