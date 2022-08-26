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
    BlackUser getBlackUserInfoByName(String username);
    BlackUser getBlackUserInfoByIdentityCard(String identityCard);
    BlackUser selectBlackUserByPrimaryKey(String id);
    BlackUser selectByPrimaryKey(String id);
    List<BlackUser> selectAllBlackUser(BlackUserPageReqVO vo);
    List<BlackUser> selectBlackUserInfoByIdentityCards(List<String> identityCards);
    List<BlackUser> getUserListByDeptId(List<String> identityCard);
    int insertSelective(BlackUser record);
    int updateByPrimaryKeySelective(BlackUser record);
    int deletedBlackUsers(@Param("sysUser") BlackUser sysUser, @Param("list") List<String> list);
}
