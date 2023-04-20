package com.yiie.common.mapper;

import com.yiie.entity.BlackUser;
import com.yiie.entity.Customer;
import com.yiie.entity.H5Background;
import com.yiie.entity.User;
import com.yiie.vo.request.BlackUserPageReqVO;
import com.yiie.vo.request.CustomerPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerMapper {
    List<String> getCustomerInfoIdsByIds(List<String> ids);
    List<String> getCustomerContactIdsByIds(List<String> ids);
    Customer getCustomerInfoByName(String username);
    Customer getCustomerByIdentityCard(String identityCard);
    Customer selectCustomerByPrimaryKey(String id);
    Customer selectByPrimaryKey(String id);
    List<Customer> selectAllCustomers(CustomerPageReqVO vo);
    List<Customer> selectCustomerInfoByIdentityCards(List<String> identityCards);
    int insertSelective(Customer record);
    int updateByPrimaryKeySelective(Customer record);
    int deletedCustomers(@Param("sysUser") Customer sysUser, @Param("list") List<String> list);

    String getIDCardByUserId(String userId);

//    User getUserInfoByName(String username);

    H5Background getBackground();

    void changeBackground(H5Background picPath);

    void insetBackground(H5Background h5Background);

    H5Background getBackgroundByUserId(String userId);

    void customerInsetBackground(H5Background h5Background);

    void customerChangeBackground(H5Background h5Background);
}