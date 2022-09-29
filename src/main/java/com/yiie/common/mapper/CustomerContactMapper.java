package com.yiie.common.mapper;

import com.yiie.entity.CustomerContact;
import com.yiie.vo.request.CustomerContactPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerContactMapper {
    CustomerContact selectCustomerContactByPrimaryKey(String id);
    List<CustomerContact> selectAllCustomerContacts(CustomerContactPageReqVO vo);
    int insertSelective(CustomerContact record);
    int updateByPrimaryKeySelective(CustomerContact record);
    int deletedCustomerContacts(@Param("sysUser") CustomerContact sysUser, @Param("list") List<String> list);
    int deletedCustomerContactsByCustomerContactIds(@Param("sysUser") CustomerContact sysUser, @Param("list") List<String> list);
}
