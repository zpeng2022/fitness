package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.CustomerContactMapper;
import com.yiie.common.service.CustomerContactService;
import com.yiie.entity.CustomerContact;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.CustomerContactAddReqVO;
import com.yiie.vo.request.CustomerContactPageReqVO;
import com.yiie.vo.request.CustomerContactUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerContactServiceImpl implements CustomerContactService {
    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Override
    public PageVO<CustomerContact> pageInfo(CustomerContactPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<CustomerContact> sysUsers = customerContactMapper.selectAllCustomerContacts(vo);
        return PageUtils.getPageVO(sysUsers);
    }

    @Override
    /**
     * we need to get the Customer_contactId
     */
    public void addCustomerContact(CustomerContactAddReqVO vo) {
        CustomerContact customerContact = new CustomerContact();
        // TODO stuff,
        customerContact.setContact_id(UUID.randomUUID().toString());
        customerContact.setCustomer_contactId(vo.getCustomer_contacts_id());
        customerContact.setContacter_phone(vo.getContacter_phone());
        customerContact.setContacter_identity_card(vo.getContacter_identity_card());
        customerContact.setContacter_display_name(vo.getContacter_display_name());
        customerContact.setContacter_deleted(1);
        int contact_return = customerContactMapper.insertSelective(customerContact);
        if (contact_return != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateCustomerContact(CustomerContactUpdateReqVO vo) {
        CustomerContact sysUser = customerContactMapper.selectCustomerContactByPrimaryKey(vo.getContact_id());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getContact_id());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setContacter_deleted(1);
        /**
         * you can do more additional operations here.
         */
        int count = customerContactMapper.updateByPrimaryKeySelective(sysUser);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedCustomerContacts(List<String> contactIds) {
        if(contactIds.contains("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_ADMIN);
        }
        CustomerContact sysUser = new CustomerContact();
        // It's same in all jobs that we have done.
        // we don't need operationID in BlackUser
        // sysUser.setUpdateId(operationId);
        sysUser.setContacter_deleted(0);
        int i = customerContactMapper.deletedCustomerContacts(sysUser,contactIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
