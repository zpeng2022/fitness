package com.yiie.common.mapper;

import com.yiie.entity.Gym;
import com.yiie.entity.GymOrder;
import com.yiie.entity.GymOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface GymOrderDetailMapper {
    List<GymOrderDetail> getAllGymOrderDetailByDeptID(String deptId);
    List<GymOrderDetail> getAllGymOrderDetailByGymID(String gymId);
    /**
     * identityCard作为索引, 或者组合索引
     * 一个人的记录很少, 一个deptId的记录就很多.
     */
    GymOrderDetail selectByIdentityCardAndTimeAndDeptId(@Param("customerIdentityCard") String customerIdentityCard, @Param("deptId") String deptId);
    GymOrderDetail selectByPrimaryKey(String tagId);
    int insertSelective(GymOrderDetail gymOrderDetail);
    int updateByPrimaryKeySelective(GymOrderDetail gymOrderDetail);
    int deletedGymOrderDetails(@Param("sysUser") GymOrderDetail sysUser, @Param("list") List<String> list);

}
