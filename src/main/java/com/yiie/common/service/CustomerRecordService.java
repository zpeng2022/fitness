package com.yiie.common.service;

import com.yiie.entity.CustomerRecord;
import com.yiie.vo.request.CustomerRecordPageReqVO;
import com.yiie.vo.response.PageVO;

import java.util.Date;

public interface CustomerRecordService {
    PageVO<CustomerRecord> h5CustomerRecordPageInfo(CustomerRecordPageReqVO vo);
    CustomerRecord h5CustomerFinalRecord(CustomerRecordPageReqVO vo);
    void h5GetNewCustomerRecord(String CustomerId, Date finalTime, Integer customerRecordCount);
}
