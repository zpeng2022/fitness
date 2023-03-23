package com.yiie.common.mapper;

import com.yiie.entity.BlackUser;
import com.yiie.entity.ExerciseType;
import com.yiie.vo.request.ExerciseTypeAddReqVO;
import com.yiie.vo.request.ExerciseTypePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExerciseTypeMapper {
    List<ExerciseType> getAllType();

    List<ExerciseType> selectAllCustomersInfo(ExerciseTypePageReqVO vo);

    int deletedExerciseType(@Param("exerciseType") ExerciseType exerciseType, @Param("list") List<String> userIds);

    int insertSelective(ExerciseType exerciseType);

    ExerciseType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ExerciseType exerciseType);

    List<ExerciseType> getAllTypeWithOutDeleted();

    List<ExerciseType> getTypeByDeptId(String deptId);
}
