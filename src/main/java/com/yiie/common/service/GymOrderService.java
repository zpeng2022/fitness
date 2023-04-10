package com.yiie.common.service;


import com.yiie.entity.GymOrder;
import com.yiie.vo.data.SportAndValue;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface GymOrderService {
    PageVO<GymOrder> h5PageInfo(GymOrderPageReqVO vo);
    PageVO<GymOrder> pageInfo(GymOrderPageReqVO vo);
    void addGymOrders(GymOrderAddReqVO vo);
    void cancelGymOrder(GymOrderCancelPageReqVO vo);
    void updateGymOrdersInfo(GymOrderUpdateReqVO vo);
    void deletedGymOrders(List<String> userIds);

    List<SportAndValue> getTypeAndValue(String name);

    List<SportAndValue> getAllTypeAndValue();

    List<SportAndValue> getGymPeopleNum();

    void passOrder(String orderId);

    void refuseOrder(String replaceAll);

    void refuseOrder2(OrderCancelVO cancelVO);

    void cancelOrder(OrderCancelVO cancelVO);

    List<GymOrder> getCanceledOrderByCustomerId(String userId);

    void readCanceledOrder(String userId);

    void delCanceledOrder(String userId);

    List<GymOrder> getUnreadCanceledOrderByCustomerId(String userId);
}
