package com.yiie.common.mapper;

import com.yiie.entity.GymCustomTags;
import com.yiie.entity.GymOrder;
import com.yiie.vo.data.SportAndValue;
import com.yiie.vo.request.GymOrderPageReqVO;
import com.yiie.vo.request.OrderCancelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface GymOrderMapper {
    List<GymOrder> getAllGymOrderByDeptID(String deptId);
    List<GymOrder> selectAllGymOrderByDeptID(GymOrderPageReqVO vo);
    List<GymOrder> selectAllGymOrderByCustomerID(GymOrderPageReqVO vo);
    List<GymOrder> getAllGymOrdersByGymID(String gymId);
    GymOrder selectByPrimaryKey(String orderId);
    int selectOrderStatusByPrimaryKey(@Param("orderId") String orderId);
    // get and sort with createTime
    List<GymOrder> getCustomerOrderSortWithCreateTime(@Param("customerId") String customerId, @Param("createTime") Date finalTime);
    // compare with orderEndTime.
    // createTime < finalTime.
    // finalTime < orderEndTime
    // 需要优化???
    List<GymOrder> getCustomerOrderSortWithOrderEndTime(@Param("customerId") String customerId, @Param("finalTime") Date finalTime);
    int insertSelective(GymOrder gymOrder);
    int updateOrderStatusWithPrimaryKey(@Param("orderId") String orderId, @Param("orderStatus") Integer orderStatus);
    int updateByPrimaryKeySelective(GymOrder gymOrder);
    int deletedGymOrders(@Param("sysUser") GymOrder sysUser, @Param("list") List<String> list);

    List<SportAndValue> getTypeAndValue(String name);

    List<SportAndValue> getAllTypeAndValue();

    List<SportAndValue> getGymPeopleNum();

    void passOrder(String orderId);

    void refuseOrder(String orderId);

    void refuseOrder2(OrderCancelVO cancelVO);

    void cancelOrder(OrderCancelVO cancelVO);

    List<GymOrder> getCanceledOrderByCustomerId(String userId);

    void readCanceledOrder(String userId);

    void delCanceledOrder(String userId);

    List<GymOrder> getUnreadCanceledOrderByCustomerId(String userId);
}
