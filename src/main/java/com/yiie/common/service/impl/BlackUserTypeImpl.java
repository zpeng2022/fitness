package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.BlackUserTypeMapper;
import com.yiie.common.service.BlackUserTypeService;
import com.yiie.entity.BlackUser;
import com.yiie.entity.BlackUserType;
import com.yiie.entity.ExerciseType;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.BlackTypeAddReqVO;
import com.yiie.vo.request.BlackTypePageReqVO;
import com.yiie.vo.request.BlackTypeUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BlackUserTypeImpl implements BlackUserTypeService {

    @Autowired
    BlackUserTypeMapper blackUserTypeMapper;


    @Override
    public PageVO<BlackUserType> pageInfo(BlackTypePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<BlackUserType> blackUserTypes = blackUserTypeMapper.selectAllTypeInfo(vo);
        return PageUtils.getPageVO(blackUserTypes);
    }

    @Override
    public void deletedBlackUserType(List<String> userIds, String operationId) {
        BlackUserType blackUserType=new BlackUserType();
        blackUserType.setUpdateTime(new Date());
        blackUserType.setDeleted(0);
        int i = blackUserTypeMapper.deletedBlackType(blackUserType,userIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void addBlackUserType(BlackTypeAddReqVO vo) {
        BlackUserType blackUserType=new BlackUserType();
        BeanUtils.copyProperties(vo,blackUserType);
        blackUserType.setCreateTime(new Date());
        blackUserType.setDeleted(1);
        int i = blackUserTypeMapper.insertSelective(blackUserType);
        if (i!=1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateBlackUserType(BlackTypeUpdateReqVO vo, String operationId) {
        BlackUserType blackUserType=blackUserTypeMapper.selectByPrimaryKey(vo.getTypeId());
        if (null == blackUserType){
            log.error("传入 的 id:{}不合法",vo.getTypeId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        /*// permission ???
        if(operationId.equals(vo.getId()) && !operationId.equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_MYSELF);
        }
        if(!operationId.equals("fcf34b56-a7a2-4719-9236-867495e74c31") && vo.getId().equals("fcf34b56-a7a2-4719-9236-867495e74c31")){
            throw new BusinessException(BaseResponseCode.OPERATION_ADMIN);
        }*/
        BeanUtils.copyProperties(vo,blackUserType);
        blackUserType.setDeleted(1);
        blackUserType.setUpdateTime(new Date());
        int count= blackUserTypeMapper.updateByPrimaryKeySelective(blackUserType);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public List<BlackUserType> getByDeptId(BlackUserType b) {
        return blackUserTypeMapper.getByDeptId(b);
    }
}
