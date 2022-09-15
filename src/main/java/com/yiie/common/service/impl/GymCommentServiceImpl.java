package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.*;
import com.yiie.common.service.*;
import com.yiie.entity.Gym;
import com.yiie.entity.GymComments;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.GymCommentAddReqVO;
import com.yiie.vo.request.GymCommentPageReqVO;
import com.yiie.vo.request.GymCommentUpdateReqVO;
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
public class GymCommentServiceImpl implements GymCommentService {

    @Autowired
    private GymCommentsMapper gymCommentsMapper;

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
    public PageVO<GymComments> pageInfo(GymCommentPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GymComments> gymComments = gymCommentsMapper.selectAllGymCommentsByDeptID(vo);
        return PageUtils.getPageVO(gymComments);
    }

    @Override
    public void addGymComments(GymCommentAddReqVO vo) {
        GymComments gymComments = new GymComments();
        BeanUtils.copyProperties(vo, gymComments);
        gymComments.setCommentId(UUID.randomUUID().toString());
        gymComments.setCommentCustomerIdentityCard(vo.getCommentCustomerIdentityCard());
        gymComments.setGymId(vo.getGymId());
        gymComments.setDeptId(vo.getDeptId());
        gymComments.setCommentId(vo.getCommentId());
        gymComments.setCommentStars(vo.getCommentStars());
        gymComments.setCommentDetail(vo.getCommentDetail());
        if(vo.getCommentStatus() != null) gymComments.setCommentStatus(vo.getCommentStatus());
        else gymComments.setCommentStatus(0);
        gymComments.setCommentTags(vo.getCommentTags());
        gymComments.setCommentCustomerId(vo.getCommentCustomerId());
        gymComments.setDeleted(1);
        gymComments.setCommentCustomerName(vo.getCommentCustomerName());
        gymComments.setCreateTime(new Date());
        int result = gymCommentsMapper.insertSelective(gymComments);
        if(result != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateGymCommentsInfo(GymCommentUpdateReqVO vo) {
        GymComments gymComments = gymCommentsMapper.selectByPrimaryKey(vo.getCommentId());
        if(null == gymComments){
            log.error("传入 的 id:{}不合法",vo.getCommentId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo, gymComments);
        gymComments.setUpdateTime(new Date());
        /**
         * update Comment Answer
         */
        if(vo.getCommentAnswer() != "") {
            gymComments.setCommentAnswer(vo.getCommentAnswer());
            // if we got the answer, we should set the status as read.
            gymComments.setCommentStatus(1);
        }
        int count = gymCommentsMapper.updateByPrimaryKeySelective(gymComments);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedGymComments(List<String> userIds) {
        GymComments gymComments = new GymComments();
        gymComments.setUpdateTime(new Date());
        gymComments.setDeleted(0);
        int result = gymCommentsMapper.deletedGymComments(gymComments, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
