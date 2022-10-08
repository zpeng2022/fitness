package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.CustomerContactMapper;
import com.yiie.common.mapper.CustomerInfoMapper;
import com.yiie.common.mapper.CustomerMapper;
import com.yiie.common.service.CustomerService;
import com.yiie.entity.Customer;
import com.yiie.entity.CustomerContact;
import com.yiie.entity.CustomerInfo;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.QRCodeGeneratorUtil;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.CustomerAddReqVO;
import com.yiie.vo.request.CustomerPageReqVO;
import com.yiie.vo.request.CustomerUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Override
    public PageVO<Customer> pageInfo(CustomerPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<Customer> sysUsers = customerMapper.selectAllCustomers(vo);
        return PageUtils.getPageVO(sysUsers);
    }

    @Override
    public void addCustomer(CustomerAddReqVO vo) throws IOException {
        Customer sysUser = new Customer();
        CustomerInfo userInfo = new CustomerInfo();

        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setCustomer_id(UUID.randomUUID().toString());
        sysUser.setCustomer_identity_card(vo.getCustomer_identity_card());
        sysUser.setCustomer_name(vo.getCustomer_name());
        sysUser.setCustomer_nickname(vo.getCustomer_nickname());
        sysUser.setCustomer_weight(vo.getCustomer_weight());
        sysUser.setCustomer_height(vo.getCustomer_height());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sysUser.setCustomer_birthday(ft.parse(vo.getCustomer_birthday()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sysUser.setCustomer_location(vo.getCustomer_location());
        sysUser.setCustomer_hobby(vo.getCustomer_hobby());
        sysUser.setCustomer_disease(vo.getCustomer_disease());
        sysUser.setCustomer_picture(vo.getCustomer_picture());
        sysUser.setCustomer_gender(vo.getCustomer_gender());
        sysUser.setCustomer_phone(vo.getCustomer_phone());
        /**
         * 暂时放的数据是 用户的身份证.
         * 自己初始化一个ORcode?
         */
        BufferedImage image = QRCodeGeneratorUtil.createImage(sysUser.getCustomer_identity_card());
        String qrString = QRCodeGeneratorUtil.bufferedImageToBase64(image);
        sysUser.setCustomer_fitness_QRCode(qrString);
        /**
         * 这个需要加到contactId里面
         */
        sysUser.setCustomer_contactId(UUID.randomUUID().toString());
        /**
         * customer_info_id
         */
        String customer_info_id = UUID.randomUUID().toString();
        sysUser.setCustomer_info_id(customer_info_id);

        userInfo.setCustomer_id(UUID.randomUUID().toString());
        userInfo.setCustomer_info_id(customer_info_id);
        /**
         * initial point of credit or fitness is 100.
         */
        userInfo.setCustomer_credit_point(100);
        userInfo.setCustomer_fitness_point(100);
        userInfo.setCustomer_step_per_day(0);
        userInfo.setCustomer_info_deleted(1);

        sysUser.setCustomer_create_time(new Date());
        sysUser.setCustomer_gender(vo.getCustomer_gender());
        sysUser.setCustomer_deleted(1);
        int customer_return = customerMapper.insertSelective(sysUser);
        int info_return = customerInfoMapper.insertSelective(userInfo);
        if (customer_return != 1 || info_return != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateCustomer(CustomerUpdateReqVO vo) throws ParseException {
        Customer sysUser = customerMapper.selectCustomerByPrimaryKey(vo.getCustomer_id());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getCustomer_id());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,sysUser);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(vo.getCustomer_birthday() != null) sysUser.setCustomer_birthday(ft.parse(vo.getCustomer_birthday()));
        sysUser.setCustomer_update_time(new Date());
        sysUser.setCustomer_deleted(1);
        // update sex.
        if(vo.getCustomer_gender() == null || vo.getCustomer_gender().equals("")){
            sysUser.setCustomer_gender(customerMapper.getCustomerInfoByName(vo.getCustomer_name()).getCustomer_gender());
        }else {
            sysUser.setCustomer_gender(vo.getCustomer_gender());
        }
        // sysUser.setUpdateId(operationId);
        int count= customerMapper.updateByPrimaryKeySelective(sysUser);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedCustomers(List<String> userIds) {
        Customer sysUser = new Customer();
        // It's same in all jobs that we have done.
        // we don't need operationID in BlackUser
        // sysUser.setUpdateId(operationId);
        sysUser.setCustomer_update_time(new Date());
        sysUser.setCustomer_deleted(0);
        // we need to delete contact and customerinfo

        // TODO: step 1, delete the userinfo in `customer_info`.
        // get info ids according to userids
        CustomerInfo sysUserInfo = new CustomerInfo();
        sysUserInfo.setCustomer_info_deleted(0);
        List<String> userInfoIds = customerMapper.getCustomerInfoIdsByIds(userIds);
        int info_result = customerInfoMapper.deletedCustomersInfo(sysUserInfo, userInfoIds);
        if(info_result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        // TODO: step 2, delete the user contacts in `customer_contact`.
        // get contact ids according to userids
        CustomerContact sysUserContact = new CustomerContact();
        sysUserContact.setContacter_deleted(0);
        List<String> userContactIds = customerMapper.getCustomerContactIdsByIds(userIds);
        int contact_result = customerContactMapper.deletedCustomerContactsByCustomerContactIds(sysUserContact, userContactIds);
        if(contact_result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        // TODO: step 3, delete the user in `customer`.
        int i = customerMapper.deletedCustomers(sysUser,userIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
