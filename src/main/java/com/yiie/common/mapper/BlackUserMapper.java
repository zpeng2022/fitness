package com.yiie.common.mapper;

import com.yiie.entity.BlackUser;
import com.yiie.vo.request.BlackUserPageReqVO;
import com.yiie.vo.request.UserPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlackUserMapper {
    BlackUser getBlackUserInfoByNameByDeptID(String username, String deptID);
    // BlackUser getBlackUserInfoByIdentityCardAndDeptID(String identityCard, String deptID);
    BlackUser selectByPrimaryKey(String id);
    // it changed
    // we get all black users with deptID.
    List<BlackUser> selectAllBlackUserWithDeptID(BlackUserPageReqVO vo);
    int insertSelective(BlackUser record);
    int updateByPrimaryKeySelective(BlackUser record);
    int deletedBlackUsers(@Param("sysUser") BlackUser sysUser, @Param("list") List<String> list);
}
