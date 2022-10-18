package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.*;
import com.yiie.common.service.CustomerRecordService;
import com.yiie.entity.*;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.CustomerRecordPageReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CustomerRecordServiceImpl implements CustomerRecordService {
    @Autowired
    private CustomerRecordMapper customerRecordMapper;

    @Autowired
    private GymHistoryMapper gymHistoryMapper;

    @Autowired
    private GymOrderMapper gymOrderMapper;

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public PageVO<CustomerRecord> h5CustomerRecordPageInfo(CustomerRecordPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        // TODO... test...
        List<CustomerRecord> customerRecordList = customerRecordMapper.getCustomerRecordsByCustomerId(vo.getCustomerId());
        return PageUtils.getPageVO(customerRecordList);
    }

    @Override
    public CustomerRecord h5CustomerFinalRecord(CustomerRecordPageReqVO vo) {
        // get the final record stored in `customer_fitnesscredithistory` table
        // TODO... test...
        // creatTime
        CustomerRecord customerRecord = customerRecordMapper.getFinalRecord(vo.getCustomerId());
        return customerRecord;
    }

    @Override
    public void h5GetNewCustomerRecord(String CustomerId, Date finalTime, Integer customerRecordCount) {
        // get new records and store them in `customer_fitnesscredithistory` table
        // step 1. get the offline record.
        Date maxHistoryTime = finalTime;
        Customer customerResult = customerMapper.selectByPrimaryKey(CustomerId);
        String customerIdentityCard = customerResult.getCustomer_identity_card();
        String customerInfoId = customerResult.getCustomer_info_id();
        // TODO... mapper not implement.
        List<GymHistory> customerOfflineHistory = gymHistoryMapper.getCustomerHistoryWithOnlineStateAndTime(customerIdentityCard, 0, finalTime);
        ArrayList<GymHistory> customerOfflineHistoryList = new ArrayList<>(customerOfflineHistory);
        int siz = customerOfflineHistoryList.size();
        System.out.println("offline history records: " + siz + " ======");
        for(int i = 0; i < siz; i ++){
            GymHistory customerHistory = customerOfflineHistoryList.get(i);
            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setRecordId(UUID.randomUUID().toString());
            customerRecord.setCustomerId(CustomerId);
            // offline record.
            customerRecord.setIsOnline(0);
            // Done state.
            customerRecord.setRecordStatus(1);
            // add one point.
            customerRecord.setAddCreditScore(1);
            customerRecord.setAddFitnessScore(1);
            customerRecord.setInGymTime(customerHistory.getInTime());
            customerRecord.setOutGymTime(customerHistory.getOutTime());
            customerRecord.setExerciseType(customerHistory.getExerciseType());
            Date createTime = customerHistory.getCreateTime();
            if(createTime.getTime() > maxHistoryTime.getTime()) maxHistoryTime = createTime;
            customerRecord.setFinalRecordTime(maxHistoryTime);
            customerRecord.setCustomerRecordCount( ++ customerRecordCount);
            customerRecord.setDeleted(1);
            // TODO... test...
            int result = customerRecordMapper.insertSelective(customerRecord);
            if(result != 1){
                log.error("插入用户健身_积分_历史记录数据异常  recordId为: ",customerRecord.getRecordId());
            }
        }
        if(siz != 0){
            // update customer info (total fitness scores and credit scores)
            CustomerInfo customerInfo = customerInfoMapper.selectCustomerInfoByPrimaryKey(customerInfoId);
            int fitnessTotalScore = customerInfo.getCustomer_fitness_point();
            customerInfo.setCustomer_fitness_point(fitnessTotalScore + siz);
            int creditTotalScore = customerInfo.getCustomer_credit_point();
            customerInfo.setCustomer_credit_point(creditTotalScore + siz);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        }

        // TODO...
        // step 2. get the online record and check their status.
        // `gymOrder` and `gymHistory` table
        // Done state and Invalid State
        List<GymHistory> customerOnlineHistory = gymHistoryMapper.getCustomerHistoryWithOnlineStateAndTime(customerIdentityCard, 1, finalTime);
        ArrayList<GymHistory> customerOnlineHistoryList = new ArrayList<>(customerOnlineHistory);
        Set<String> existOrderIds = new HashSet<>();
        int addFitnessScore = 0;
        int addCreditScore = 0;
        siz = customerOnlineHistoryList.size();
        if(customerOnlineHistory == null) siz = 0;
        System.out.println("online history records: " + siz + " ======");
        for(int i = 0; i < siz; i ++){
            GymHistory gymHistory = customerOnlineHistoryList.get(i);
            int temFitnessScore = 1;
            addFitnessScore += temFitnessScore;
            int temCreditScore = 1;
            int temStatus = 1;
            String orderId = gymHistory.getOrderId();
            existOrderIds.add(orderId);
            // orderId in the `history`, it tells that this record is already passed.
            // int orderStatus = gymOrderMapper.selectOrderStatusByPrimaryKey(orderId);
            Date outGymTime = gymHistory.getOutTime();
            if(outGymTime == null){
                temCreditScore = -1;
                // state is 4, which means that customer didn't record while he/she walking out of the gym.
                temStatus = 4;
            }
            addCreditScore += temCreditScore;
            // set the customer record.
            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setRecordId(UUID.randomUUID().toString());
            customerRecord.setCustomerId(CustomerId);
            // online record.
            customerRecord.setIsOnline(1);
            // Done state.
            customerRecord.setRecordStatus(temStatus);
            // add one point.
            customerRecord.setAddCreditScore(temCreditScore);
            customerRecord.setAddFitnessScore(temFitnessScore);
            customerRecord.setOutGymTime(gymHistory.getOutTime());
            customerRecord.setInGymTime(gymHistory.getInTime());
            Date createTime = gymHistory.getCreateTime();
            if(createTime.getTime() > maxHistoryTime.getTime()) maxHistoryTime = createTime;
            customerRecord.setFinalRecordTime(maxHistoryTime);
            customerRecord.setExerciseType(gymHistory.getExerciseType());
            customerRecord.setOrderStartTime(gymHistory.getOrderStartTime());
            customerRecord.setOrderEndTime(gymHistory.getOrderEndTime());
            customerRecord.setCustomerRecordCount( ++ customerRecordCount);
            customerRecord.setDeleted(1);
            int result = customerRecordMapper.insertSelective(customerRecord);
            if(result != 1){
                log.error("插入用户健身_积分_历史记录数据异常  recordId为: ",customerRecord.getRecordId());
            }
        }

        // TODO...
        // check the status of the order.
        // step 3. get cancelled orders
        // and the orderId not in gymHistory.
        // TODO... mapper not implement.
        int passState = 1;
        List<GymOrder> customerOrders = gymOrderMapper.getCustomerOrderSortWithCreateTime(CustomerId, finalTime);
        // existOrderIds...
        ArrayList<GymOrder> customerOrderList = new ArrayList<>(customerOrders);
        siz = customerOrderList.size();
        if(customerOrders == null) siz = 0;

        System.out.println("finalTime: " + finalTime + " ======");
        System.out.println("order records: " + siz + " ======");
        int passCount = 0;

        for(int i = 0; i < siz; i ++){
            GymOrder gymOrder = customerOrderList.get(i);
            String orderId = gymOrder.getOrderId();
            Date orderEndTime = gymOrder.getOrderEndTime();
            int orderStatus = gymOrder.getOrderStatus();
            if(orderStatus == 0) continue;
            int temFitnessScore = 1, temCreditScore = 1, recordState = 1;
            // TODO... check an element in a set or not.
            Date nowDate = new Date();
            if(orderStatus == 1 && !existOrderIds.contains(orderId) && (orderEndTime.getTime() <= nowDate.getTime())){
                recordState = 3;
                temFitnessScore = -1;
                temCreditScore = -1;
                if(orderEndTime.getTime() > maxHistoryTime.getTime()) maxHistoryTime = orderEndTime;
            }else if(orderStatus == 2){
                // cancelled order.
                recordState = 2;
                temCreditScore = 0;
                temFitnessScore = 0;
                Date createTime = gymOrder.getCreateTime();
                if(createTime.getTime() > maxHistoryTime.getTime()) maxHistoryTime = createTime;
            }else{
                continue;
            }

            passCount ++;
            addCreditScore += temCreditScore;
            addFitnessScore += temFitnessScore;

            // set the customer record.
            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setCustomerId(CustomerId);
            customerRecord.setRecordId(UUID.randomUUID().toString());
            // online record.
            customerRecord.setIsOnline(1);
            // Done state.
            customerRecord.setRecordStatus(recordState);
            // add one point.
            customerRecord.setAddCreditScore(temCreditScore);
            customerRecord.setAddFitnessScore(temFitnessScore);
            customerRecord.setOrderStartTime(gymOrder.getOrderStartTime());
            customerRecord.setOrderEndTime(gymOrder.getOrderEndTime());
            customerRecord.setExerciseType(gymOrder.getExerciseType());
            customerRecord.setCustomerRecordCount( ++ customerRecordCount);
            // TODO... set the right time.
            customerRecord.setFinalRecordTime(maxHistoryTime);
            customerRecord.setDeleted(1);
            int result = customerRecordMapper.insertSelective(customerRecord);
            if(result != 1){
                log.error("插入用户健身_积分_历史记录数据异常  recordId为: ",customerRecord.getRecordId());
            }
        }

        System.out.println("order records passCount: " + passCount + " ======");


        // step 4.
        // TODO... check passed Orders with future orderStartTime.
        // getCustomerOrderSortWithOrderEndTime
        // TODO... createTime < finalTime.
        // TODO... finalTime < orderEndTime
        // update finalTime with maxHistoryTime
        List<GymOrder> customerOtherOrders = gymOrderMapper.getCustomerOrderSortWithOrderEndTime(CustomerId, finalTime);
        ArrayList<GymOrder> customerOtherOrdersList = new ArrayList<>(customerOtherOrders);
        siz = customerOtherOrdersList.size();
        if(customerOtherOrders == null) siz = 0;
        System.out.println("check passed Orders with future orderStartTime: " + siz);
        for(int i = 0; i < siz; i ++){
            GymOrder gymOrder = customerOtherOrdersList.get(i);
            String orderId = gymOrder.getOrderId();
            int orderStatus = gymOrder.getOrderStatus();
            if(orderStatus != 1) continue;
            // orderEndTime < maxHistoryTime
            Date orderEndTime = gymOrder.getOrderEndTime();
            if(orderEndTime.getTime() > maxHistoryTime.getTime()) continue;

            int temFitnessScore = 1, temCreditScore = 1, recordState = 1;
            if(!existOrderIds.contains(orderId)){
                recordState = 3;
                temCreditScore = -1;
                temFitnessScore = -1;
            }
            addCreditScore += temCreditScore;
            addFitnessScore += temFitnessScore;
            // set the customer record.
            CustomerRecord customerRecord = new CustomerRecord();
            customerRecord.setRecordId(UUID.randomUUID().toString());
            customerRecord.setCustomerId(CustomerId);
            customerRecord.setIsOnline(1);
            customerRecord.setRecordStatus(recordState);
            customerRecord.setAddCreditScore(temCreditScore);
            customerRecord.setAddFitnessScore(temFitnessScore);
            customerRecord.setOrderStartTime(gymOrder.getOrderStartTime());
            customerRecord.setOrderEndTime(gymOrder.getOrderEndTime());
            // set the right Time.
            customerRecord.setFinalRecordTime(maxHistoryTime);
            customerRecord.setExerciseType(gymOrder.getExerciseType());
            customerRecord.setCustomerRecordCount( ++ customerRecordCount);
            customerRecord.setDeleted(1);
            int result = customerRecordMapper.insertSelective(customerRecord);
            if(result != 1){
                log.error("插入用户健身_积分_历史记录数据异常  recordId为: ",customerRecord.getRecordId());
            }
        }

        System.out.println("=======> addCreditScore: " + addCreditScore);
        System.out.println("=======> addFitnessScore: " + addCreditScore);
        if(addCreditScore != 0 && addCreditScore != 0){
            // update customer fitness score and credit score.
            CustomerInfo customerInfo = customerInfoMapper.selectCustomerInfoByPrimaryKey(customerInfoId);
            int creditTotalScore = customerInfo.getCustomer_credit_point();
            customerInfo.setCustomer_credit_point(creditTotalScore + addCreditScore);
            int fitnessTotalScore = customerInfo.getCustomer_fitness_point();
            customerInfo.setCustomer_fitness_point(fitnessTotalScore + addFitnessScore);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        }
        return;
    }
}
