package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.*;
import com.yiie.common.service.*;
import com.yiie.entity.Gym;
import com.yiie.entity.GymCustomTags;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.GymCommentTagAddReqVO;
import com.yiie.vo.request.GymCommentTagPageReqVO;
import com.yiie.vo.request.GymCommentTagUpdateReqVO;
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
public class GymCommentTagServiceImpl implements GymCommentTagService {

    @Autowired
    private GymCustomTagsMapper gymCustomTagsMapper;

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
    public PageVO<GymCustomTags> pageInfo(GymCommentTagPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GymCustomTags> gymCustomTags = gymCustomTagsMapper.selectAllGymCustomTagsByDeptID(vo);
        return PageUtils.getPageVO(gymCustomTags);
    }

    @Override
    public void addGymCustomTags(GymCommentTagAddReqVO vo) {
        GymCustomTags gymCustomTags = new GymCustomTags();
        BeanUtils.copyProperties(vo, gymCustomTags);
        gymCustomTags.setTagId(UUID.randomUUID().toString());
        gymCustomTags.setTagContent(vo.getTagContent());
        gymCustomTags.setTagCount(vo.getTagCount());
        gymCustomTags.setDeptId(vo.getDeptId());
        gymCustomTags.setCreateTime(new Date());
        gymCustomTags.setDeleted(1);
        int result = gymCustomTagsMapper.insertSelective(gymCustomTags);
        if(result != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateGymCustomTagsInfo(GymCommentTagUpdateReqVO vo) {
        GymCustomTags gymCustomTags = gymCustomTagsMapper.selectByPrimaryKey(vo.getTagId());
        if(null == gymCustomTags){
            log.error("传入 的 id:{}不合法",vo.getTagId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,gymCustomTags);
        gymCustomTags.setDeleted(1);
        gymCustomTags.setUpdateTime(new Date());
        int count = gymCustomTagsMapper.updateByPrimaryKeySelective(gymCustomTags);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedGymCustomTags(List<String> userIds) {
        GymCustomTags gymCustomTags = new GymCustomTags();
        gymCustomTags.setUpdateTime(new Date());
        gymCustomTags.setDeleted(0);
        int result = gymCustomTagsMapper.deletedGymCustomTags(gymCustomTags, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
