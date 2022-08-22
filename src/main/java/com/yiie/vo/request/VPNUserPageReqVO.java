package com.yiie.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Time： 2020-3-10 11:48
 * Email： yiie315@163.com
 * Desc：
 *
 * @Author： yiie
 * @Version： 1.0
 */

@Data
public class VPNUserPageReqVO {

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 ")
    private Integer status;

    @ApiModelProperty(value = "创建开始时间")
    private String startTime;

    @ApiModelProperty(value = "创建结束时间")
    private String endTime;

    @ApiModelProperty(value = "会员等级")
    private String level;
}
