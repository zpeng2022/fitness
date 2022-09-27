package com.yiie.common.mapper;

import com.yiie.entity.GymComments;
import com.yiie.vo.request.GymCommentPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymCommentsMapper {
    List<GymComments> getAllGymCommentsByDeptID(String deptId);
    List<GymComments> getAllGymCommentsByGymID(String gymId);
    List<GymComments> selectAllGymCommentsByDeptID(GymCommentPageReqVO vo);
    List<GymComments> selectAllGymComments(GymCommentPageReqVO vo);
    GymComments selectByPrimaryKey(String commentId);
    int insertSelective(GymComments gymComments);
    int updateByPrimaryKeySelective(GymComments gymComments);
    int deletedGymComments(@Param("sysUser") GymComments sysUser, @Param("list") List<String> list);
}
