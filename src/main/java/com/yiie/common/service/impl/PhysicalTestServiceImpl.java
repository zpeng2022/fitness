package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.PhysicalTestMapper;
import com.yiie.common.service.PhysicalTestService;
import com.yiie.entity.Article;
import com.yiie.entity.Gym;
import com.yiie.entity.PhysicalTestData;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.vo.request.BlackUserUpdateReqVO;
import com.yiie.vo.request.PhysicalTestReqVO;
import com.yiie.vo.response.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PhysicalTestServiceImpl implements PhysicalTestService {
    @Autowired
    PhysicalTestMapper physicalTestMapper;

    @Override
    public PageVO<PhysicalTestData> pageInfo(PhysicalTestReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<PhysicalTestData> physicalTestData = physicalTestMapper.selectData(vo);
        return PageUtils.getPageVO(physicalTestData);
    }

    @Override
    public void deletedPhysical(List<String> userIds, String operationId) {
        PhysicalTestData physicalTestData = new PhysicalTestData();
        physicalTestData.setUpdateTime(new Date());
        physicalTestData.setDeleted(0);
        int result = physicalTestMapper.deletedPs(physicalTestData, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void addPhysical(PhysicalTestData vo) {
        physicalTestMapper.addPhysical(vo);
    }

    @Override
    public void updatePhysical(PhysicalTestData vo) {
        int count = physicalTestMapper.updateByPrimaryKeySelective(vo);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public PhysicalTestData getByCustomerId(String userId) {
        return null;
    }
}
