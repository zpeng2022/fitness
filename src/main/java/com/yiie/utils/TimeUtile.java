package com.yiie.utils;

import io.swagger.models.auth.In;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class TimeUtile {
    static Scanner sc = new Scanner(System.in);
    static Calendar c=Calendar.getInstance();
    public static void main(String[] args) {
        System.out.println("请输入身份证：");
        String IDcard = sc.next();
        IDcard(IDcard);
    }

    //计算日期差
    //天数
    public static int getTimeDiffer_day(Date form) throws ParseException {//现在减过去
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format.format(form);
        form=format.parse(date1);
        String date2 = format.format(new Date());
        Date now = format.parse(date2);
        int a = (int) ((form.getTime() - now.getTime()) / (1000*3600*24));
        return a;
    }

    public static String IDcardGetBirth(String IDcard) {
        switch(IDcard.length()) {
            case 18:
            case 16:
                String year = IDcard.substring(6, 10);
                if(Integer.decode(year) <1900 && Integer.decode(year)>c.get(Calendar.YEAR))
                    System.out.println("年龄不合法");
                String month = IDcard.substring(10,12);

                if(Integer.decode(month)<1 && Integer.decode(month)>12)
                    System.out.println("身份证不合法");
                String day = IDcard.substring(12,14);

                c.set(Integer.decode(year) ,Integer.decode(month), 0);
                if(Integer.decode(day)>c.get(Calendar.DAY_OF_MONTH))
                    System.out.println("身份证不合法");
                System.out.println("尊贵的用户，您的生日为："+year+"年"+month+"月"+day+"日");
                StringBuffer s=new StringBuffer();
                s.append(year);
                s.append("-");
                s.append(month);
                s.append("-");
                s.append(day);
                return s.toString();
            default:
                System.out.println("身份证不合法");
                return null;
        }
    }
    public static int IDcard(String IDcard) {
        switch(IDcard.length()) {
            case 18:
            case 16:
                String year = IDcard.substring(6, 10);
                if(Integer.decode(year) <1900 && Integer.decode(year)>c.get(Calendar.YEAR))
                    System.out.println("年龄不合法");
                String month = IDcard.substring(10,12);

                if(Integer.decode(month)<1 && Integer.decode(month)>12)
                    System.out.println("身份证不合法");
                String day = IDcard.substring(12,14);

                c.set(Integer.decode(year) ,Integer.decode(month), 0);
                if(Integer.decode(day)>c.get(Calendar.DAY_OF_MONTH))
                    System.out.println("身份证不合法");
                System.out.println("尊贵的用户，您的生日为："+year+"年"+month+"月"+day+"日");
                return Integer.parseInt(year);
            default:
                System.out.println("身份证不合法");
                return 0;
        }
    }
    /**
     *      * 在给定的日期加上或减去指定月份后的日期
     *      *
     *      * @param sourceDate 原始时间
     *      * @param month      要调整的月份，向前为负数，向后为正数
     *      * @return
     *
     */
    public static Date stepHour(Date sourceDate, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
    public static Date stepDay(Date sourceDate, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.DAY_OF_WEEK, day);
        return c.getTime();
    }
    public static Date toIntegral(Date sourceDate) throws ParseException {//转整点
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=df2.parse(df.format(sourceDate));
        return day;
    }
    public static Date getDayStart(Date sourceDate) throws ParseException {//转整点
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=df2.parse(df.format(sourceDate));
        return day;
    }
    public static Date getDayEnd(Date sourceDate) throws ParseException {//转整点
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=df2.parse(df.format(sourceDate));
        return day;
    }
    public static Integer getWeekDay(Date sourceDate) throws ParseException {//星期星期一~星期六：1-6 星期天：0
        Calendar cal=Calendar.getInstance();
        cal.setTime(sourceDate);
        int day=cal.get(Calendar.DAY_OF_WEEK)-1;
        return day;
    }
    public static Float getHourDiffer(String hour1,String hour2,String hour3) throws ParseException {
        //h1-h2:预计开闭时间，h3：实际闭馆时间
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date d1=df.parse(hour1);
        Date d2=df.parse(hour2);
        Date d3=df.parse(hour3);
        float differ;
        //加abs防止出现负的情况
        if(d3.getTime()<=Math.min(d1.getTime(),d2.getTime())){//小于开馆时间
            differ=0f;
        }else if(d3.getTime()>=Math.max(d1.getTime(),d2.getTime())){//大于闭馆时间
            differ=(float)Math.abs(d2.getTime()-d1.getTime())/3600000;
        }else {//介于中间
            differ=(float)Math.abs(d3.getTime()-Math.min(d1.getTime(),d2.getTime()))/3600000;
        }
        return differ;
    }
    public static void test5() {
        String workDay="9:00-11:30 1:30-5:00";
        String h1=workDay.substring(0,workDay.indexOf("-"));
        String h2=workDay.substring(workDay.indexOf("-")+1);
        String h3=workDay.substring(0,workDay.indexOf("-"));
        String h4=workDay.substring(workDay.indexOf("-")+1);
    }
}
