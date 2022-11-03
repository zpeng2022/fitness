package com.yiie.vo.data;

import lombok.Data;

import java.util.List;

@Data
public class ContainData {
    private String gymId;//Id
    private String gymName;//场馆名称
    private List<OnlineNum> onlineNums;//上下线人数
    private List<SportAndValue> ageList;//场馆预约成员年龄分布
    private List<SportAndValue> evList;//场馆各运动类型人数分布
    private List<Integer> orderTimeList;//场地预约时间分布
    private List<SportAndValue> sportRank;//运动排行榜
    private List<Integer> todayPeoples;//今日各时段人数分布
    private List<GymPeopleMonth> lastMonthPeople;//近一个月场地人数
    private List<String> openTime;//计算开放时长的日期
    private List<Integer> exceptOpenTime;//预计开放时长
    private List<Integer> actualOpenTime;//实际开放时长
    private List<String> allGymName;//所有场馆名称

    @Override
    public String toString() {
        return "ContainData{" +
                "gymId='" + gymId + '\'' +
                ", gymName='" + gymName + '\'' +
                ", \nageList=\n" + ageList +
                ", \nevList=\n" + evList +
                ", \norderTimeList=\n" + orderTimeList +
                ", \nsportRank=\n" + sportRank +
                ", \ntodayPeoples=\n" + todayPeoples +
                ", \nlastMonthPeople=\n" + lastMonthPeople +
                ", \nopenTime=\n" + openTime +
                ", \nexceptOpenTime=\n" + exceptOpenTime +
                ", \nactualOpenTime=\n" + actualOpenTime +
                '}';
    }
}
