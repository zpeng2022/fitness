package com.yiie.common.mapper;

import com.yiie.entity.CustomerInfo;
import com.yiie.vo.request.CustomerInfoPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerInfoMapper {
    CustomerInfo selectCustomerInfoByPrimaryKey(String id);
    List<CustomerInfo> selectAllCustomersInfo(CustomerInfoPageReqVO vo);
    int insertSelective(CustomerInfo record);
    int updateByPrimaryKeySelective(CustomerInfo record);
    int deletedCustomersInfo(@Param("sysUser") CustomerInfo sysUser, @Param("list") List<String> list);
}
