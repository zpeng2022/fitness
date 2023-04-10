package com.yiie.vo.response;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class FollowsInfo {
    private String name;
    private String identityCard;
    private String phone;
    private Integer isBlack;
}
