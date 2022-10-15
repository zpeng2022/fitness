package com.yiie.common.mapper;

import com.yiie.entity.GymOrder;
import com.yiie.vo.request.GymOrderPageReqVO;
import com.yiie.vo.data.SportAndValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymOrderMapper {
    List<GymOrder> getAllGymOrderByDeptID(String deptId);
    List<GymOrder> selectAllGymOrderByDeptID(GymOrderPageReqVO vo);
    List<GymOrder> getAllGymOrdersByGymID(String gymId);
    GymOrder selectByPrimaryKey(String tagId);
    int insertSelective(GymOrder gymOrder);
    int updateByPrimaryKeySelective(GymOrder gymOrder);
    int deletedGymOrders(@Param("sysUser") GymOrder sysUser, @Param("list") List<String> list);

    List<SportAndValue> getTypeAndValue(String name);

    List<SportAndValue> getAllTypeAndValue();

    List<SportAndValue> getGymPeopleNum();
}
