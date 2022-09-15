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

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        gymOrder.setOrderStartTime(vo.getOrderStartTime());
        gymOrder.setOrderEndTime(vo.getOrderEndTime());
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
             * we should put all the people in the order to order detail
             */
            String [] identityCards = otherCustomerIdentityCards.split("#");
            Integer len = identityCards.length;
            identityCards[len] = firstIdentityCard;
            String otherCustomerNames = vo.getOtherCustomerNames();
            String otherCustomerPhones = vo.getOtherCustomerPhones();
            String [] customerNames = otherCustomerNames.split("#");
            customerNames[len] = vo.getCustomerName();
            String [] customerPhones = otherCustomerPhones.split("#");
            customerPhones[len] = vo.getCustomerPhone();
            len ++;
            for(int i = 0; i < len; i ++){
                GymOrderDetail gymOrderDetail = new GymOrderDetail();
                gymOrderDetail.setOrderDetailId(UUID.randomUUID().toString());
                gymOrderDetail.setGymId(vo.getGymId());
                gymOrderDetail.setDeptId(vo.getDeptId());
                gymOrderDetail.setDeleted(1);
                gymOrderDetail.setOrderId(orderId);
                gymOrderDetail.setCustomerName(customerNames[i]);
                gymOrderDetail.setCustomerPhone(customerPhones[i]);
                gymOrderDetail.setCustomerName(customerNames[i]);
                gymOrderDetail.setIsBlackUser(0);
                gymOrderDetail.setCreateTime(new Date());
                gymOrderDetail.setExerciseType(vo.getExerciseType());
                gymOrderDetail.setOrderStartTime(vo.getOrderStartTime());
                gymOrderDetail.setOrderEndTime(vo.getOrderEndTime());

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
    public void updateGymOrdersInfo(GymOrderUpdateReqVO vo) {
        GymOrder gymOrder = gymOrderMapper.selectByPrimaryKey(vo.getOrderId());
        if(null == gymOrder){
            log.error("传入 的 id:{}不合法",vo.getOrderId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo, gymOrder);
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

            String [] identityCards = otherCustomerIdentityCards.split("#");
            Integer len = identityCards.length;
            identityCards[len] = firstIdentityCard;
            String otherCustomerNames = gymOrder.getOtherCustomerNames();
            String otherCustomerPhones = gymOrder.getOtherCustomerPhones();
            String [] customerNames = otherCustomerNames.split("#");
            customerNames[len] = gymOrder.getCustomerName();
            String [] customerPhones = otherCustomerPhones.split("#");
            customerPhones[len] = gymOrder.getCustomerPhone();
            len ++;
            for(int i = 0; i < len; i ++){
                GymOrderDetail gymOrderDetail = new GymOrderDetail();
                gymOrderDetail.setOrderDetailId(UUID.randomUUID().toString());
                gymOrderDetail.setGymId(gymOrder.getGymId());
                gymOrderDetail.setDeptId(gymOrder.getDeptId());
                gymOrderDetail.setDeleted(1);
                gymOrderDetail.setOrderId(orderId);
                gymOrderDetail.setCustomerName(customerNames[i]);
                gymOrderDetail.setCustomerPhone(customerPhones[i]);
                gymOrderDetail.setCustomerName(customerNames[i]);
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
