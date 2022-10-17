package com.yiie.common.mapper;

import com.yiie.entity.CustomerInfo;
import com.yiie.entity.CustomerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerRecordMapper {
    // TODO... need to test...
    List<CustomerRecord> getCustomerRecordsByCustomerId(String customerId);
    int insertSelective(CustomerRecord customerRecord);
    CustomerRecord getFinalRecord(String customerId);
    int deletedCustomerRecords(@Param("list") List<String> recordIds);
}
