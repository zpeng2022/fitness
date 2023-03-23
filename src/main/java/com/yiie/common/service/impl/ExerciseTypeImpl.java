package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.ExerciseTypeMapper;
import com.yiie.common.service.ExerciseTypeService;
import com.yiie.entity.BlackUser;
import com.yiie.entity.CustomerInfo;
import com.yiie.entity.ExerciseType;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.ExerciseTypeAddReqVO;
import com.yiie.vo.request.ExerciseTypePageReqVO;
import com.yiie.vo.request.ExerciseTypeUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExerciseTypeImpl implements ExerciseTypeService {
    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Override
    public List<ExerciseType> getAllType() {
        return exerciseTypeMapper.getAllType();
    }

    @Override
    public PageVO<ExerciseType> pageInfo(ExerciseTypePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<ExerciseType> exerciseTypes = exerciseTypeMapper.selectAllCustomersInfo(vo);
        return PageUtils.getPageVO(exerciseTypes);
    }

    @Override
    public void deletedExerciseType(List<String> userIds, String operationId) {
        ExerciseType exerciseType=new ExerciseType();
        exerciseType.setUpdateTime(new Date());
        exerciseType.setDeleted(0);
        int i = exerciseTypeMapper.deletedExerciseType(exerciseType,userIds);
        if (i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public List<ExerciseType> getAllTypeWithOutDeleted() {
        return exerciseTypeMapper.getAllTypeWithOutDeleted();
    }

    @Override
    public List<ExerciseType> getTypeByDeptId(String deptId) {
        return exerciseTypeMapper.getTypeByDeptId(deptId);
    }

    @Override
    public void addExerciseType(ExerciseTypeAddReqVO vo) {
        ExerciseType exerciseType=new ExerciseType();
        BeanUtils.copyProperties(vo,exerciseType);
        exerciseType.setCreateTime(new Date());
        exerciseType.setDeleted(1);
        int i = exerciseTypeMapper.insertSelective(exerciseType);
        if (i!=1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateExerciseType(ExerciseTypeUpdateReqVO vo, String operationId) {
        ExerciseType exerciseType = exerciseTypeMapper.selectByPrimaryKey(vo.getTypeId());
        if (null == exerciseType){
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
        BeanUtils.copyProperties(vo,exerciseType);
        exerciseType.setDeleted(1);
        exerciseType.setUpdateTime(new Date());
        int count= exerciseTypeMapper.updateByPrimaryKeySelective(exerciseType);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
