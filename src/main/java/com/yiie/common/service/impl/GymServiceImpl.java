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
import com.yiie.vo.data.GymIsClose;
import com.yiie.vo.data.GymOpenTimeVO;
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
import java.util.Map;
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
        String k1=vo.getWorkDay1().replaceAll(" ","");
        String k2=vo.getWorkDay2().replaceAll(" ","");
        gym.setMonday(k1+" "+k2);
//        System.out.print(k1+"  "+k2);
        gym.setSaturday(vo.getWeekend1().replaceAll(" ","")+" "+vo.getWeekend2().replaceAll(" ",""));
//        System.out.print("\n\n\n\n\n\naddGym:"+gym+"\n\n\n\n\n\n");
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
        //更新时间格式
        if(vo.getWorkDay1()!=null&&vo.getWorkDay2()!=null)
            gym.setMonday(vo.getWorkDay1()+" "+vo.getWorkDay2());
        if(vo.getWeekend1()!=null&&vo.getWeekend2()!=null)
            gym.setSaturday(vo.getWeekend1()+" "+vo.getWeekend2());
//        System.out.print("\n\n\n图片上传gym:"+gym+"\n\n");
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

    @Override
    public Gym getById(String gymId) {
//        return gymMapper.selectByPrimaryKey(gymId);
        return gymMapper.getPicPath(gymId);
    }

    @Override
    public void autoPassBydeptId(String deptId) {
        gymMapper.autoPassBydeptId(deptId);
    }

    @Override
    public List<Gym>  getByName(String name,String deptId) {
        return gymMapper.getByName(name,deptId);
    }

    @Override
    public List<GymOpenTimeVO> getGymOT() {
        return gymMapper.getGymOT();
    }

    @Override
    public List<String> selectAllName() {
        return gymMapper.selectAllName();
    }

    @Override
    public List<GymIsClose> getIsClose() {
        return gymMapper.getIsClose();
    }

    @Override
    public List<String> getAllTypes() {
        return gymMapper.getAllTypes();
    }

    @Override
    public List<Gym> getByDeptId(String deptId) {
        return gymMapper.getByDeptId(deptId);
    }

    @Override
    public List<String> selectAllNameById(String deptId) {
        return gymMapper.selectAllNameById(deptId);
    }

    @Override
    public List<Gym> getAllGym(String name) {
        return gymMapper.getAllGym(name);
    }

    @Override
    public List<GymOpenTimeVO> getGymOTByDeptId(String deptId) {
        return gymMapper.getGymOTByDeptId(deptId);
    }

    @Override
    public List<Gym> getAll() {
        return gymMapper.getAll();
    }

    @Override
    public List<String> getTypesByDeptId(String deptId) {
        return gymMapper.getTypesByDeptId(deptId);
    }

    @Override
    public List<Gym> getGymByDeptId(String deptId) {
        return gymMapper.getGymByDeptId(deptId);
    }
}
