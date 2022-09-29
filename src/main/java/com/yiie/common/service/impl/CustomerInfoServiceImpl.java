package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.CustomerInfoMapper;
import com.yiie.common.mapper.CustomerMapper;
import com.yiie.common.service.CustomerInfoService;
import com.yiie.entity.Customer;
import com.yiie.entity.CustomerInfo;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.CustomerInfoAddReqVO;
import com.yiie.vo.request.CustomerInfoPageReqVO;
import com.yiie.vo.request.CustomerInfoUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerInfoServiceImpl implements CustomerInfoService {
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public PageVO<CustomerInfo> pageInfo(CustomerInfoPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<CustomerInfo> sysUsers = customerInfoMapper.selectAllCustomersInfo(vo);
        return PageUtils.getPageVO(sysUsers);
    }

    @Override
    public void updateCustomerInfo(CustomerInfoUpdateReqVO vo) {
        CustomerInfo sysUser = customerInfoMapper.selectCustomerInfoByPrimaryKey(vo.getCustomer_info_id());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getCustomer_info_id());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        // permission ?? we just passed it.
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setCustomer_info_deleted(1);
        int count= customerInfoMapper.updateByPrimaryKeySelective(sysUser);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedCustomersInfo(List<String> infoIds) {
        if(infoIds.contains("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_ADMIN);
        }
        CustomerInfo sysUser = new CustomerInfo();
        // It's same in all jobs that we have done.
        // we don't need operationID in BlackUser
        // sysUser.setUpdateId(operationId);
        sysUser.setCustomer_info_deleted(0);
        int i = customerInfoMapper.deletedCustomersInfo(sysUser, infoIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
