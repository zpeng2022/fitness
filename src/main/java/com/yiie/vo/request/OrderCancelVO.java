package com.yiie.vo.request;

import lombok.Data;

@Data
public class OrderCancelVO {
    private String orderId;
    private String gymId;
    private String timespan;
    private String reason;
    private String start;
    private String end;
}
