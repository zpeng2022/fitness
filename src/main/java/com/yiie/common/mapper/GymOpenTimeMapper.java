package com.yiie.common.mapper;

import com.yiie.entity.GymOpenTime;
import com.yiie.vo.data.GymCloseTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 注意openTime 和 closeTime 是字符串存储的.
 * 拿到一个gym的openTime可以根据whichday排序
 * order by whichday 添加一个索引
 */
@Repository
@Mapper
public interface GymOpenTimeMapper {
    List<GymOpenTime> getAllGymOpenTimeByNameByDeptID(String deptId);
    GymOpenTime selectByPrimaryKey(String gymId);
    int insertSelective(GymOpenTime gymOpenTime);
    int updateByPrimaryKeySelective(GymOpenTime gymOpenTime);
    int deletedGymOpenTime(@Param("sysUser") GymOpenTime sysUser, @Param("list") List<String> list);

    List<GymCloseTime> getGymCT(String gymId, Date today, Date fiveDaysAgo);
    GymOpenTime getByIdAndDay(String s, Date today);

    List<GymCloseTime> getGymCT2(String sG, Date today, Date fiveDaysAgo, String deptId);
}
