package com.yiie.common.service;

import com.yiie.entity.ExerciseType;
import com.yiie.vo.request.ExerciseTypeAddReqVO;
import com.yiie.vo.request.ExerciseTypePageReqVO;
import com.yiie.vo.request.ExerciseTypeUpdateReqVO;
import com.yiie.vo.response.PageVO;

import java.util.List;

public interface ExerciseTypeService {
    List<ExerciseType> getAllType();

    PageVO<ExerciseType> pageInfo(ExerciseTypePageReqVO vo);

    void addExerciseType(ExerciseTypeAddReqVO vo);

    void updateExerciseType(ExerciseTypeUpdateReqVO vo, String operationId);

    void deletedExerciseType(List<String> userIds, String operationId);

    List<ExerciseType> getAllTypeWithOutDeleted();

    List<ExerciseType> getTypeByDeptId(String deptId);
}
