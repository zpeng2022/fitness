package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.CustomerContactMapper;
import com.yiie.common.mapper.CustomerInfoMapper;
import com.yiie.common.mapper.CustomerMapper;
import com.yiie.common.mapper.LoginLogMapper;
import com.yiie.common.service.*;
import com.yiie.constant.Constant;
import com.yiie.entity.*;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.*;
import com.yiie.vo.request.*;
import com.yiie.vo.response.LoginRespVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private int LOGIN_FAIL_CNT = 10;
    private int LOGIN_FAIL_TIME = 60;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private BlackUserService blackUserService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

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
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd");
        String birth=TimeUtile.IDcardGetBirth(vo.getCustomer_identity_card());
//        System.out.print("-------------------"+birth+"\n\n\n");
        try {
//            sysUser.setCustomer_birthday(ft.parse(vo.getCustomer_birthday()));
            sysUser.setCustomer_birthday(ft2.parse(birth));
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
//        sysUser.setCustomer_fitness_QRCode(qrString);
        sysUser.setCustomer_fitness_QRCode("fitness_QRCode");
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
        System.out.print("-------------------"+sysUser+"\n\n\n");
        int customer_return = customerMapper.insertSelective(sysUser);
        int info_return = customerInfoMapper.insertSelective(userInfo);
        if (customer_return != 1 || info_return != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateCustomer(CustomerUpdateReqVO vo) throws ParseException {
        /*Customer sysUser = customerMapper.selectCustomerByPrimaryKey(vo.getCustomer_id());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getCustomer_id());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        String createTime = vo.getCustomer_create_time();
        BeanUtils.copyProperties(vo,sysUser);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysUser.setCustomer_create_time(ft.parse(createTime));
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
        }*/
        Customer sysUser = customerMapper.selectCustomerByPrimaryKey(vo.getCustomer_id());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getCustomer_id());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        String createTime1 = vo.getCustomer_create_time();
        //  String createTime1 = "2022-11-26T10:13:07.000+0000";
        int len1 = "2022-11-26".length();
        int len2 = "10:13:07".length();
        char[] chars = createTime1.toCharArray();
        chars[len1] = ' ';
        String createTime = new String(chars).substring(0,len1+len2+1);

        BeanUtils.copyProperties(vo,sysUser);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysUser.setCustomer_create_time(ft.parse(createTime));
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

    @Override
    public String getIDCardByUserId(String userId) {
        return customerMapper.getIDCardByUserId(userId);
    }

    public void saveloginLog(LoginLog log){
        loginLogMapper.saveLoginLog(log);
    }

    @Override
    public LoginRespVO login(CustomerLoginVo vo) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        LoginLog loginLog = new LoginLog();
        String ip = IPUtil.getIpAddr(request);
        // 判断该用户是否被锁
        Map<String, Object> retMap = new HashMap<String, Object>();
        boolean isLock = redisService.isLoginLock(ip);
        System.out.println("======================================用户是否被锁：" + isLock);
        if(isLock) {
            loginLog.setStatus(0);
            loginLog.setMsg("IP被锁定");
            loginLog.setCode(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG.getCode());
            saveloginLog(loginLog);
            throw new BusinessException(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG);
        }
        if(ip != null){
            // 某时间间隔内ip错误次数
            Long retriesLockNum = redisService.getLoginRetriesLockNum(ip);
            System.out.println("==================================" + ip + " 失败次数 " + retriesLockNum);
//            if(retriesLockNum != null && retriesLockNum < LOGIN_FAIL_CNT && retriesLockNum > 0) {
//                System.out.println("==================================" + ip + " 可以正常登录 ，删除了redis记录");
//                //登录成功后删除redis记录
//                redisService.delete(ip);
//            } else
            if(retriesLockNum >= LOGIN_FAIL_CNT) {// 处理并发
                isLock = redisService.isLoginLock(ip);
                if (!isLock) {
                    redisService.loginLock(ip, 60 * LOGIN_FAIL_TIME);
                }
                loginLog.setStatus(0);
                loginLog.setMsg("IP被锁定");
                loginLog.setCode(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG.getCode());
                saveloginLog(loginLog);
                throw new BusinessException(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG);
            }
        }
        Long retriesLockNum = redisService.setloginRetriesLockNum(ip, 60);
        if(retriesLockNum != null && retriesLockNum >= LOGIN_FAIL_CNT) {
            isLock = redisService.loginLock(ip, 60*LOGIN_FAIL_TIME);
            loginLog.setStatus(0);
            loginLog.setMsg("IP被锁定");
            loginLog.setCode(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG.getCode());
            saveloginLog(loginLog);
            throw new BusinessException(BaseResponseCode.IP_LOGIN_FAILCOUNT_BIG);
        }
        loginLog.setUsername(vo.getName());
        loginLog.setLoginTime(new Date());
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        loginLog.setSystemBrowserInfo();
        loginLog.setStatus(1);
//        System.out.print("\n------VO："+vo+"\n\n\n");
        Customer customer = customerMapper.getCustomerByIdentityCard(vo.getIdentityCard());
        if(null == customer){
            loginLog.setStatus(0);
            loginLog.setMsg("用户不存在");
            loginLog.setCode(BaseResponseCode.NOT_ACCOUNT.getCode());
            saveloginLog(loginLog);
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        BlackUser blackUser=blackUserService.getByIdentityCard(vo.getIdentityCard());
        Boolean isBlack=false;
        if(blackUser!=null)
            isBlack=true;
        if(isBlack){
            loginLog.setStatus(0);
            loginLog.setMsg("customer是黑名单");
            loginLog.setCode(BaseResponseCode.USER_LOCK.getCode());
            saveloginLog(loginLog);
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        /*if(!PasswordUtil.matches(user.getSalt(),vo.getPassword(),user.getPassword())){
            loginLog.setStatus(0);
            loginLog.setMsg("密码错误");
            redisService.incrby(ip,0L);
            loginLog.setCode(BaseResponseCode.PASSWORD_ERROR.getCode());
            saveloginLog(loginLog);
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }*/
        LoginRespVO respVO=new LoginRespVO();
        BeanUtils.copyProperties(customer,respVO);
        Map<String,Object> claims=new HashMap<>();
        claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(customer.getCustomer_id()));
        claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(customer.getCustomer_id()));
        claims.put(Constant.JWT_USER_NAME,customer.getCustomer_name());
        String access_token= JwtTokenUtil.getAccessToken(customer.getCustomer_id(),claims);
        String refresh_token;
        if(vo.getType().equals("1")){
            refresh_token=JwtTokenUtil.getRefreshToken(customer.getCustomer_id(),claims);
        }else {
            refresh_token=JwtTokenUtil.getRefreshAppToken(customer.getCustomer_id(),claims);
        }
        respVO.setAccessToken(access_token);
        respVO.setRefreshToken(refresh_token);
        loginLog.setCode(0);
        loginLog.setMsg("登录成功");
        loginLog.setStatus(1);
        redisService.delete(ip);
        saveloginLog(loginLog);
        return respVO;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken)||StringUtils.isEmpty(refreshToken)){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        log.info("subject.getPrincipals()={}",subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String customerId=JwtTokenUtil.getUserId(accessToken);

        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+accessToken,customerId,JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);

        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken,customerId,JwtTokenUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);


        redisService.delete(Constant.IDENTIFY_CACHE_KEY+customerId);
    }

    @Override
    public String refreshToken(String refreshToken, String accessToken) {
        if(redisService.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST+refreshToken)||!JwtTokenUtil.validateToken(refreshToken)){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        String customerId=JwtTokenUtil.getUserId(refreshToken);
        log.info("customerId={}",customerId);
        Customer customer=customerMapper.selectByPrimaryKey(customerId);
        if (null==customer){
            throw new BusinessException(BaseResponseCode.TOKEN_PARSE_ERROR);
        }
        Map<String,Object> claims=null;

        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+customerId)){
            claims=new HashMap<>();
            claims.put(Constant.JWT_ROLES_KEY,getRolesByUserId(customerId));
            claims.put(Constant.JWT_PERMISSIONS_KEY,getPermissionsByUserId(customerId));
        }
        String newAccessToken=JwtTokenUtil.refreshToken(refreshToken,claims);

        redisService.setifAbsen(Constant.JWT_REFRESH_STATUS+accessToken,customerId,1,TimeUnit.MINUTES);

        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+customerId)){
            redisService.set(Constant.JWT_REFRESH_IDENTIFICATION+newAccessToken,customerId,redisService.getExpire(Constant.JWT_REFRESH_KEY+customerId,TimeUnit.MILLISECONDS),TimeUnit.MILLISECONDS);
        }
        return newAccessToken;
    }

    @Override
    public Customer getCustomerById(String userId) {
        return customerMapper.getCustomerByIdentityCard(userId);
    }

    @Override
    public H5Background getBackground() {
        return customerMapper.getBackground();
    }

    @Override
    public void changeBackground(H5Background picPath) {
        customerMapper.changeBackground(picPath);
    }

    @Override
    public void insetBackground(H5Background h5Background) {
        customerMapper.insetBackground(h5Background);
    }

    @Override
    public H5Background getBackgroundByUserId(String userId) {
        return customerMapper.getBackgroundByUserId(userId);
    }

    @Override
    public void customerInsetBackground(H5Background h5Background) {
        customerMapper.customerInsetBackground(h5Background);
    }

    @Override
    public void customerChangeBackground(H5Background h5Background) {
        customerMapper.customerChangeBackground(h5Background);
    }

    private List<String> getRolesByUserId(String userId){
        return  roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId){
        return  permissionService.getPermissionsByUserId(userId);
    }
}
