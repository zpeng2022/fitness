package com.yiie.common.mapper;

import com.yiie.entity.PhysicalTestData;
import com.yiie.vo.request.PhysicalTestReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PhysicalTestMapper {
    List<PhysicalTestData> selectData(PhysicalTestReqVO vo);

    int deletedPs(@Param("sysUser")PhysicalTestData physicalTestData,@Param("list") List<String> userIds);

    void addPhysical(PhysicalTestData vo);

    int updateByPrimaryKeySelective(PhysicalTestData vo);
}
