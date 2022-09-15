package com.yiie.common.mapper;

import com.yiie.entity.GymCustomTags;
import com.yiie.vo.request.GymCommentTagPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GymCustomTagsMapper {
    List<GymCustomTags> getAllGymCustomTagsByDeptID(String deptId);
    GymCustomTags selectByPrimaryKey(String tagId);
    List<GymCustomTags> selectAllGymCustomTagsByDeptID(GymCommentTagPageReqVO vo);
    int insertSelective(GymCustomTags gymCustomTags);
    int updateByPrimaryKeySelective(GymCustomTags gymCustomTags);
    int deletedGymCustomTags(@Param("sysUser") GymCustomTags sysUser, @Param("list") List<String> list);
}
