package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.BlackUserMapper;
import com.yiie.common.mapper.DeptMapper;
import com.yiie.common.mapper.LoginLogMapper;
import com.yiie.common.mapper.UserMapper;
import com.yiie.common.service.*;
import com.yiie.constant.Constant;
import com.yiie.entity.BlackUser;
import com.yiie.entity.Dept;
import com.yiie.entity.User;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.PasswordUtil;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.BlackUserAddReqVo;
import com.yiie.vo.request.BlackUserPageReqVO;
import com.yiie.vo.request.BlackUserUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BlackUserServiceImpl implements BlackUserService{
    @Autowired
    private BlackUserMapper blackuserMapper;

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
    public PageVO<BlackUser> pageInfo(BlackUserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<BlackUser> sysUsers = blackuserMapper.selectAllBlackUser(vo);
        /*for(BlackUser ele : sysUsers){
            System.out.println(ele.toString());
        }*/
        return PageUtils.getPageVO(sysUsers);
    }

    @Override
    public void addBlackUser(BlackUserAddReqVo vo) {
        BlackUser sysUser = new BlackUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setIdentityCard(vo.getIdentityCard());
        sysUser.setUsername(vo.getUsername());
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setImportTime(new Date());
        sysUser.setSex(vo.getSex());
        sysUser.setDeleted(1);
        int i = blackuserMapper.insertSelective(sysUser);
        if (i!=1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateBlackUserInfo(BlackUserUpdateReqVO vo, String operationId) {
        BlackUser sysUser = blackuserMapper.selectByPrimaryKey(vo.getId());
        if (null == sysUser){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        // permission ???
        if(operationId.equals(vo.getId()) && !operationId.equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_MYSELF);
        }
        if(!operationId.equals("fcf34b56-a7a2-4719-9236-867495e74c31") && vo.getId().equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_ADMIN);
        }
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setUpdateTime(new Date());
        if(vo.getSex() == null || vo.getSex().equals("")){
            sysUser.setSex(blackuserMapper.getBlackUserInfoByName(vo.getUsername()).getSex());
        }else {
            sysUser.setSex(vo.getSex());
        }
        // sysUser.setUpdateId(operationId);
        int count= blackuserMapper.updateByPrimaryKeySelective(sysUser);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletedBlackUsers(List<String> userIds, String operationId) {
        if(userIds.contains(operationId)){
            throw new BusinessException(BaseResponseCode.DELETE_CONTAINS_MYSELF);
        }
        if(userIds.contains("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_ADMIN);
        }
        BlackUser sysUser = new BlackUser();
        // we don't need operationID in BlackUser
        // sysUser.setUpdateId(operationId);
        sysUser.setUpdateTime(new Date());
        sysUser.setDeleted(0);
        int i = blackuserMapper.deletedBlackUsers(sysUser,userIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
