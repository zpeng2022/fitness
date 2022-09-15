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
    BlackUser getBlackUserInfoByNameByDeptID(@Param("username") String username, @Param("deptID") String deptID);
    BlackUser getBlackUserInfoByIdentityCardAndDeptID(@Param("identityCard")  String identityCard, @Param("deptID") String deptID);
    List<BlackUser> selectBlackUserInfoByIdentityCards(@Param("list") List<String> list, @Param("deptID") String deptID);
    BlackUser selectByPrimaryKey(String id);
    // it changed
    // we get all black users with deptID.
    List<BlackUser> selectAllBlackUserWithDeptID(BlackUserPageReqVO vo);
    int insertSelective(BlackUser record);
    int updateByPrimaryKeySelective(BlackUser record);
    int deletedBlackUsers(@Param("sysUser") BlackUser sysUser, @Param("list") List<String> list);
}
