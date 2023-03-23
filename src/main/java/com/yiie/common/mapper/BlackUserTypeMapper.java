package com.yiie.common.mapper;

import com.yiie.entity.BlackUserType;
import com.yiie.entity.ExerciseType;
import com.yiie.vo.request.BlackTypePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlackUserTypeMapper {

    List<BlackUserType> selectAllTypeInfo(BlackTypePageReqVO vo);

    int deletedBlackType(@Param("blackType") BlackUserType blackType, @Param("list") List<String> userIds);

    int insertSelective(BlackUserType blackUserType);

    BlackUserType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(BlackUserType blackUserType);

    List<BlackUserType> getByDeptId(BlackUserType bt);
}
