package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.wuwenze.poi.util.BeanUtil;
import com.yiie.common.mapper.*;
import com.yiie.common.service.*;
import com.yiie.entity.Dept;
import com.yiie.entity.Gym;
import com.yiie.entity.User;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.GymAddReqVO;
import com.yiie.vo.request.GymPageReqVO;
import com.yiie.vo.request.GymSearchNamePageReqVO;
import com.yiie.vo.request.GymUpdateReqVO;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GymServiceImpl implements GymService {

    @Autowired
    private GymMapper gymMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private GymCountMapper gymCountMapper;

    @Autowired
    private GymOpenTimeMapper gymOpenTimeMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private TokenSettings tokenSettings;


    @Override
    public PageVO<Gym> h5PageInfo(GymPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<Gym> gyms = gymMapper.h5GetAllGyms(vo);
        if(!gyms.isEmpty()){
            for (Gym gym : gyms){
                Dept sysDept = deptMapper.selectByPrimaryKey(gym.getDeptId());
                if (sysDept!=null){
                    gym.setDeptName(sysDept.getName());
                }
            }
        }
        return PageUtils.getPageVO(gyms);
    }

    @Override
    public PageVO<Gym> h5GymSearch(GymSearchNamePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        //System.out.println(gymName.get(0) + "+++++++++++++++++++++");
        List<Gym> gyms = gymMapper.h5SearchGyms(vo.getGymName());
        //if(gyms != null) System.out.println("++++++ DONE ++++++++" );
        if(!gyms.isEmpty()){
            for (Gym gym : gyms){
                Dept sysDept = deptMapper.selectByPrimaryKey(gym.getDeptId());
                if (sysDept!=null){
                    gym.setDeptName(sysDept.getName());
                }
            }
        }
        return PageUtils.getPageVO(gyms);
    }

    @Override
    public PageVO<Gym> pageInfo(GymPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<Gym> gyms = gymMapper.selectAllGymsByDeptID(vo);
        /**
         * TODO: we need to add images to it.
         */
        if(!gyms.isEmpty()){
            for (Gym gym : gyms){
                Dept sysDept = deptMapper.selectByPrimaryKey(gym.getDeptId());
                if (sysDept!=null){
                    gym.setDeptName(sysDept.getName());
                }
            }
        }
        return PageUtils.getPageVO(gyms);
    }

    @Override
    public void addGym(GymAddReqVO vo) {
        /**
         * we got deptId in it.
         */
        Gym gym = new Gym();
        BeanUtils.copyProperties(vo,gym);
        // generate new gymId
        gym.setGymId(UUID.randomUUID().toString());
        gym.setDeptId(vo.getDeptId());
        gym.setGymName(vo.getGymName());
        gym.setMonday(vo.getMonday());
        gym.setSaturday(vo.getMonday());
        gym.setGymPhone(vo.getGymPhone());
        gym.setGymCapacity(vo.getGymCapacity());
        // TODO... add images
        gym.setGymPicturesPath(vo.getGymPicturesPath());
        gym.setGymTypes(vo.getGymTypes());
        gym.setGymPosition(vo.getGymPosition());
        gym.setGymDetails(vo.getGymDetails());
        // TODO... add GPS
        gym.setGymGps(vo.getGymGps());
        gym.setCreateTime(new Date());
        gym.setDeleted(1);
        int result = gymMapper.insertSelective(gym);
        if(result != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public Gym getGymById(String Id) {
        return gymMapper.selectByPrimaryKey(Id);
    }

    @Override
    public void updateGymInfo(GymUpdateReqVO vo) {
        Gym gym = gymMapper.selectByPrimaryKey(vo.getGymId());
        if(null == gym){
            log.error("传入 的 id:{}不合法",vo.getGymId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,gym);
        gym.setDeleted(1);
        gym.setUpdateTime(new Date());
        int count = gymMapper.updateByPrimaryKeySelective(gym);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedGyms(List<String> userIds) {
        Gym gym = new Gym();
        gym.setUpdateTime(new Date());
        gym.setDeleted(0);
        int result = gymMapper.deletedGyms(gym, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
