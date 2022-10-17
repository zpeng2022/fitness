package com.yiie.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.yiie.common.mapper.*;
import com.yiie.common.service.*;
import com.yiie.entity.BlackUser;
import com.yiie.entity.GymComments;
import com.yiie.entity.GymHistory;
import com.yiie.entity.GymOrderDetail;
import com.yiie.enums.BaseResponseCode;
import com.yiie.exceptions.BusinessException;
import com.yiie.utils.PageUtils;
import com.yiie.utils.TokenSettings;
import com.yiie.vo.request.*;
import com.yiie.vo.response.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GymHistoryServiceImpl implements GymHistoryService {
    @Autowired
    private BlackUserMapper blackUserMapper;

    @Autowired
    private GymOrderDetailMapper gymOrderDetailMapper;

    @Autowired
    private GymHistoryMapper gymHistoryMapper;

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
    public PageVO<GymHistory> pageInfo(GymHistoryPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<GymHistory> GymHistory = gymHistoryMapper.selectAllGymHistoriesByDeptID(vo);
        return PageUtils.getPageVO(GymHistory);
    }

    @Override
    public void addGymHistories(GymHistoryAddReqVO vo) {
        GymHistory gymHistory = new GymHistory();
        BeanUtils.copyProperties(vo, gymHistory);
        gymHistory.setHistoryId(UUID.randomUUID().toString());
        gymHistory.setGymId(vo.getGymId());
        gymHistory.setDeptId(vo.getDeptId());
        gymHistory.setCustomerName(vo.getCustomerName());
        gymHistory.setCustomerPhone(vo.getCustomerPhone());
        gymHistory.setCustomerIdentityCard(vo.getCustomerIdentityCard());
        // check the whether is blackUser
        BlackUser blackUser = blackUserMapper.getBlackUserInfoByIdentityCardAndDeptID(vo.getCustomerIdentityCard(), vo.getDeptId());
        Integer isBlackUser = (blackUser != null) ? 1 : 0;
        gymHistory.setIsBlackUser(isBlackUser);
        GymOrderDetail gymOrderDetail = gymOrderDetailMapper.selectByIdentityCardAndTimeAndDeptId(vo.getCustomerIdentityCard(), vo.getDeptId());
        Integer isOnlineUser = (gymOrderDetail != null) ? 1 : 0;
        // set the origin orderId for online user.
        // do this, we can find whether this customer come or not.
        gymHistory.setOrderId(gymOrderDetail.getOrderId());
        gymHistory.setIsOnlineUser(isOnlineUser);
        if(gymOrderDetail != null){
            gymHistory.setOrderStartTime(gymOrderDetail.getOrderStartTime());
            gymHistory.setOrderEndTime(gymOrderDetail.getOrderEndTime());
        }
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            gymHistory.setInTime(ft.parse(vo.getInTime()));
        } catch (ParseException e) {
            log.error("time不合法 :{}不合法",vo.getInTime());
            e.printStackTrace();
        }
        gymHistory.setExerciseType(vo.getExerciseType());
        if(vo.getInTime() == null) gymHistory.setInTime(new Date());
        gymHistory.setCreateTime(new Date());
        gymHistory.setDeleted(1);
        int result = gymHistoryMapper.insertSelective(gymHistory);
        if(result != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void updateGymHistoriesInfo(GymHistoryUpdateReqVO vo) {
        GymHistory gymHistory = gymHistoryMapper.selectByPrimaryKey(vo.getHistoryId());
        if(null == gymHistory){
            log.error("传入 的 id:{}不合法",vo.getHistoryId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo, gymHistory);
        gymHistory.setDeleted(1);
        gymHistory.setUpdateTime(new Date());
        if(gymHistory.getOutTime() == null) {
            Date newTime = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(vo.getOutTime() != null) {
                try {
                    newTime = ft.parse(vo.getOutTime());
                } catch (ParseException e) {
                    log.error("time不合法 :{}不合法",vo.getOutTime());
                    e.printStackTrace();
                }
            }
            gymHistory.setOutTime(newTime);
        }
        int count = gymHistoryMapper.updateByPrimaryKeySelective(gymHistory);
        if(count != 1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public void deletedGymHistories(List<String> userIds) {
        GymHistory gymHistory = new GymHistory();
        gymHistory.setUpdateTime(new Date());
        gymHistory.setDeleted(0);
        int result = gymHistoryMapper.deletedGymHistories(gymHistory, userIds);
        if(result == 0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }
}
