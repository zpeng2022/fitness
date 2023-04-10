package com.yiie.common.mapper;

import com.yiie.entity.Gym;
import com.yiie.vo.data.GymIsClose;
import com.yiie.vo.data.GymOpenTimeVO;
import com.yiie.vo.request.GymPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface GymMapper {
//    List<Gym> getAllGymsByDeptID(String deptId);
    List<Gym> selectAllGymsByDeptID(GymPageReqVO vo);
    List<Gym> h5GetAllGyms(GymPageReqVO vo);
    Gym selectByPrimaryKey(String gymId);
    Gym getPicPath(String gymId);
    int insertSelective(Gym gym);
    int updateByPrimaryKeySelective(Gym gym);
    int deletedGyms(@Param("sysUser") Gym sysUser, @Param("list") List<String> list);

    List<Gym> h5SearchGyms(@Param("gymName") String gymName);

    void autoPassBydeptId(String deptId);

    List<Gym>  getByName(String name,String deptId);

    List<GymOpenTimeVO> getGymOT();

    List<String> selectAllName();

    List<GymIsClose> getIsClose();

    List<String> getAllTypes();

    List<Gym> getByDeptId(String deptId);

    List<String> selectAllNameById(String deptId);

    List<Gym> getAllGym(String name);

    List<GymOpenTimeVO> getGymOTByDeptId(String deptId);

    List<Gym> getAll();

    List<String> getTypesByDeptId(String deptId);

    List<Gym> getGymByDeptId(String deptId);
}
