package com.yiie.common.service;

import com.yiie.entity.Customer;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CustomerService {
    PageVO<Customer> pageInfo(CustomerPageReqVO vo);
    void addCustomer(CustomerAddReqVO vo) throws IOException;
    void updateCustomer(CustomerUpdateReqVO vo) throws ParseException;
    void deletedCustomers(List<String> userIds);
}
