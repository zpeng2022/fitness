package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.*;
import com.yiie.common.service.*;
import com.yiie.entity.GymHistory;
import com.yiie.entity.GymOrder;
import com.yiie.entity.GymOrderAutoPass;
import com.yiie.entity.GymOrderDetail;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class GymOrderServiceImpl implements GymOrderService {

    @Autowired
    private GymOrderMapper gymOrderMapper;

    @Autowired
    private GymOrderDetailMapper gymOrderDetailMapper;

    @Autowired
    private BlackUserMapper blackUserMapper;

    @Autowired
    private GymOrderAutoPassMapper gymOrderAutoPassMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private GymCountMapper gymCountMapper;

    @Autowired
    private GymOpenTimeMapper gymOpenTimeMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private TokenSettings tokenSettings;


    @Override
    public PageVO<GymOrder> h5PageInfo(GymOrderPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GymOrder> gymOrders = gymOrderMapper.selectAllGymOrderByCustomerID(vo);
        return PageUtils.getPageVO(gymOrders);
        //selectAllGymOrderByCustomerID
    }

    @Override
    public PageVO<GymOrder> pageInfo(GymOrderPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GymOrder> gymOrders = gymOrderMapper.selectAllGymOrderByDeptID(vo);
        return PageUtils.getPageVO(gymOrders);
    }

    @Override
    public void addGymOrders(GymOrderAddReqVO vo) {
        GymOrder gymOrder = new GymOrder();
        BeanUtils.copyProperties(vo, gymOrder);
        String orderId = UUID.randomUUID().toString();
        gymOrder.setOrderId(orderId);
        gymOrder.setGymId(vo.getGymId());
        gymOrder.setDeptId(vo.getDeptId());
        gymOrder.setCustomerId(vo.getCustomerId());
        gymOrder.setCustomerName(vo.getCustomerName());
        gymOrder.setCustomerPhone(vo.getCustomerPhone());
        gymOrder.setCustomerIdentityCard(vo.getCustomerIdentityCard());
        gymOrder.setExerciseType(vo.getExerciseType());
        gymOrder.setOrderFailComment(vo.getOrderFailComment());
        gymOrder.setOtherCustomerIdentityCards(vo.getOtherCustomerIdentityCards());
        gymOrder.setOtherCustomerNames(vo.getOtherCustomerNames());
        gymOrder.setOtherCustomerPhones(vo.getOtherCustomerPhones());
        gymOrder.setCreateTime(new Date());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            gymOrder.setOrderStartTime(ft.parse(vo.getOrderStartTime()));
        } catch (ParseException e) {
            log.error("time不合法 :{}不合法",vo.getOrderStartTime());
            e.printStackTrace();
        }
        try {
            gymOrder.setOrderEndTime(ft.parse(vo.getOrderEndTime()));
        } catch (ParseException e) {
            log.error("time不合法 :{}不合法",vo.getOrderEndTime());
            e.printStackTrace();
        }
        gymOrder.setDeleted(1);

        Integer existBlackUser = 0;
        gymOrder.setExistBlackUser(0);
        String deptId = vo.getDeptId();
        String firstCustomerId = vo.getCustomerId();
        String otherCustomerIdentityCards = vo.getOtherCustomerIdentityCards();
        String firstIdentityCard = customerMapper.selectByPrimaryKey(firstCustomerId).getCustomer_identity_card();
        if(blackUserMapper.getBlackUserInfoByIdentityCardAndDeptID(firstIdentityCard, deptId) != null) {
            gymOrder.setExistBlackUser(1);
        }else if(otherCustomerIdentityCards != ""){
            /**
             * check other customer's identity card
             */
            String [] identityCards = otherCustomerIdentityCards.split("#");
            for(int i = 0; i < identityCards.length; i ++){
                String temIdentityCard = identityCards[i];
                if(blackUserMapper.getBlackUserInfoByIdentityCardAndDeptID(temIdentityCard, deptId) != null){
                    existBlackUser = 1;
                    break;
                }
            }
        }

        if(existBlackUser == 1) {
            /**
             * there are black users, and the order will not pass.
             */
            gymOrder.setOrderStatus(0);
            gymOrder.setExistBlackUser(1);
            gymOrder.setOrderFailComment("存在禁入人员, 预约失败!");
        }
        GymOrderAutoPass gymOrderAutoPass = gymOrderAutoPassMapper.getGymOrderAutoPassByDeptID(vo.getDeptId());
        if(gymOrderAutoPass.getIsAutoPass() == 1 && existBlackUser == 0) {
            gymOrder.setOrderStatus(1);
            /**
             * this order is passed.
             * we should put all the people in the order to order_detail
             */
            List<String> ListidentityCards = Arrays.asList(otherCustomerIdentityCards.split("#"));
            ArrayList<String> identityCards = new ArrayList<>(ListidentityCards);
            identityCards.add(firstIdentityCard);
            String otherCustomerNames = vo.getOtherCustomerNames();
            String otherCustomerPhones = vo.getOtherCustomerPhones();
            List<String> ListCustomerNames = Arrays.asList(otherCustomerNames.split("#"));
            ArrayList customerNames = new ArrayList<>(ListCustomerNames);
            customerNames.add(vo.getCustomerName());
            List<String> ListCustomerPhones = Arrays.asList(otherCustomerPhones.split("#"));
            ArrayList customerPhones = new ArrayList<>(ListCustomerPhones);
            customerPhones.add(vo.getCustomerPhone());
            int siz = customerPhones.size();
            for(int i = 0; i < siz; i ++){
                GymOrderDetail gymOrderDetail = new GymOrderDetail();
                gymOrderDetail.setOrderDetailId(UUID.randomUUID().toString());
                gymOrderDetail.setGymId(vo.getGymId());
                gymOrderDetail.setDeptId(vo.getDeptId());
                gymOrderDetail.setDeleted(1);
                gymOrderDetail.setOrderId(orderId);
                gymOrderDetail.setCustomerName((String)customerNames.get(i));
                gymOrderDetail.setCustomerPhone((String)customerPhones.get(i));
                gymOrderDetail.setCustomerIdentityCard((String)identityCards.get(i));
                gymOrderDetail.setIsBlackUser(0);
                gymOrderDetail.setCreateTime(new Date());
                gymOrderDetail.setExerciseType(vo.getExerciseType());
                try {
                    gymOrderDetail.setOrderStartTime(ft.parse(vo.getOrderStartTime()));
                } catch (ParseException e) {
                    log.error("time不合法 :{}不合法",vo.getOrderStartTime());
                    e.printStackTrace();
                }
                try {
                    gymOrderDetail.setOrderEndTime(ft.parse(vo.getOrderEndTime()));
                } catch (ParseException e) {
                    log.error("time不合法 :{}不合法",vo.getOrderEndTime());
                    e.printStackTrace();
                }
                int result = gymOrderDetailMapper.insertSelective(gymOrderDetail);
                if(result != 1){
                    throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
                }
            }
        }
        /**
         * finally insert the total order, which is shown on the web page.
         */
        int result = gymOrderMapper.insertSelective(gymOrder);
        if(result != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void cancelGymOrder(GymOrderCancelPageReqVO vo) {
        int cancelStatus = 2;
        int result = gymOrderMapper.updateOrderStatusWithPrimaryKey(vo.getOrderId(), cancelStatus);
        if(result != 1){
            log.error("cancelGymOrder Failed!", vo.getOrderId());
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateGymOrdersInfo(GymOrderUpdateReqVO vo) {
        GymOrder gymOrder = gymOrderMapper.selectByPrimaryKey(vo.getOrderId());
        if(null == gymOrder){
            log.error("传入 的 id:{}不存在",vo.getOrderId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Date createTime = gymOrder.getCreateTime();
        BeanUtils.copyProperties(vo, gymOrder);
        gymOrder.setDeleted(1);
        gymOrder.setCreateTime(createTime);
        gymOrder.setUpdateTime(new Date());
        if(vo.getOrderFailComment() != null) gymOrder.setOrderFailComment(vo.getOrderFailComment());
        if(vo.getOrderStatus() != 0) gymOrder.setOrderStatus(vo.getOrderStatus());
        /**
         * check whether the order is passed, or the auto-pass is on.
         */
        Integer status = 0;
        if(gymOrder.getOrderStatus() == 1) status = 1;
        if(gymOrderAutoPassMapper.getGymOrderAutoPassByDeptID(gymOrder.getDeptId()).getIsAutoPass() == 1) status = 1;
        if(status == 1){
            String otherCustomerIdentityCards = gymOrder.getOtherCustomerIdentityCards();
            String orderId = gymOrder.getOrderId();
            String firstIdentityCard = gymOrder.getCustomerIdentityCard();

            List<String> ListIdentityCards = Arrays.asList(otherCustomerIdentityCards.split("#"));
            ArrayList identityCards = new ArrayList<>(ListIdentityCards);
            identityCards.add(firstIdentityCard);
            String otherCustomerNames = gymOrder.getOtherCustomerNames();
            String otherCustomerPhones = gymOrder.getOtherCustomerPhones();
            List<String> ListCustomerNames = Arrays.asList(otherCustomerNames.split("#"));
            ArrayList customerNames = new ArrayList<>(ListCustomerNames);
            customerNames.add(gymOrder.getCustomerName());
            List<String> ListCustomerPhones = Arrays.asList(otherCustomerPhones.split("#"));
            ArrayList customerPhones = new ArrayList<>(ListCustomerPhones);
            customerPhones.add(gymOrder.getCustomerPhone());
            int siz = customerPhones.size();
            for(int i = 0; i < siz; i ++){
                GymOrderDetail gymOrderDetail = new GymOrderDetail();
                gymOrderDetail.setOrderDetailId(UUID.randomUUID().toString());
                gymOrderDetail.setGymId(gymOrder.getGymId());
                gymOrderDetail.setDeptId(gymOrder.getDeptId());
                gymOrderDetail.setDeleted(1);
                gymOrderDetail.setOrderId(orderId);
                gymOrderDetail.setCustomerName((String)customerNames.get(i));
                gymOrderDetail.setCustomerPhone((String)customerPhones.get(i));
                gymOrderDetail.setCustomerIdentityCard((String)identityCards.get(i));
                gymOrderDetail.setIsBlackUser(0);
                gymOrderDetail.setCreateTime(new Date());
                gymOrderDetail.setExerciseType(gymOrder.getExerciseType());
                gymOrderDetail.setOrderStartTime(gymOrder.getOrderStartTime());
                gymOrderDetail.setOrderEndTime(gymOrder.getOrderEndTime());

                int result = gymOrderDetailMapper.insertSelective(gymOrderDetail);
                if(result != 1){
                    throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
                }
            }
        }

        int count = gymOrderMapper.updateByPrimaryKeySelective(gymOrder);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedGymOrders(List<String> userIds) {
        GymOrder gymOrder = new GymOrder();
        gymOrder.setUpdateTime(new Date());
        gymOrder.setDeleted(0);
        int result = gymOrderMapper.deletedGymOrders(gymOrder, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
