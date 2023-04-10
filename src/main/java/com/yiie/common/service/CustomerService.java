package com.yiie.common.service;

import com.yiie.entity.Customer;
import com.yiie.entity.H5Background;
import com.yiie.vo.request.*;
import com.yiie.vo.response.LoginRespVO;
import com.yiie.vo.response.PageVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CustomerService {
    PageVO<Customer> pageInfo(CustomerPageReqVO vo);
    void addCustomer(CustomerAddReqVO vo) throws IOException;
    void updateCustomer(CustomerUpdateReqVO vo) throws ParseException;
    void deletedCustomers(List<String> userIds);

    String getIDCardByUserId(String userId);

    LoginRespVO login(CustomerLoginVo vo);

    void logout(String accessToken, String refreshToken);

    String refreshToken(String refreshToken, String accessToken);

    Customer getCustomerById(String userId);

    H5Background getBackground();

    void changeBackground(H5Background picPath);

    void insetBackground(H5Background h5Background);

    H5Background getBackgroundByUserId(String userId);

    void customerInsetBackground(H5Background h5Background);

    void customerChangeBackground(H5Background h5Background);
}
