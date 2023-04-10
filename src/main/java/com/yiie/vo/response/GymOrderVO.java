package com.yiie.vo.response;

import com.yiie.entity.GymOrder;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GymOrderVO {
    private PageVO<GymOrder> pageVO;
    private List<Integer> followsIsBlack;
}
