package com.yiie.vo.data;

import lombok.Data;

import java.util.List;

@Data
public class UserSportData {
    private List<SportAndValue> sportKind;//运动类型
    private List<SportAndValue> sportTime;//运动时长
}
