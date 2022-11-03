import com.alibaba.excel.EasyExcel;
import com.yiie.common.service.GymOrderService;
import com.yiie.common.service.GymService;
import com.yiie.entity.Gym;
import com.yiie.utils.ExcelListener;
import com.yiie.utils.OSS;
import com.yiie.utils.TimeUtile;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.data.SportAndValue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class BagManApplicationTest {
    @Autowired
    private GymOrderService gymOrderService;
    @Autowired
    private GymService gymService;


    @Test
    public void videoAvatarLoad(){
//        String filePath="C:\\Users\\Lenovo\\Desktop\\毕业\\毕设项目资料\\视频封面\\计算机组成.jpg";
//        String filePath="C:\\Users\\Lenovo\\Desktop\\xl.png";
        String filePath="C:\\Users\\Lenovo\\Desktop\\default.png";
        String videoAvatarName=filePath.substring(filePath.lastIndexOf("\\")+1);
        String bookBucketAddress="gymPicture/"+videoAvatarName;
        OSS util=new OSS();
//        util.bookUpload(filePath,bookBucketAddress);
    }

    @Test
    public void ExcelWriteTest(){
        //实现Excel写操作

        //设置写入文件夹地址和excel文件名称
        String filename="C:\\Users\\Lenovo\\Desktop\\excelwrite.xlsx";
        /**调用easyexcel实现写操作
         * 参数：
         *  1.文件路径参数
         *  2.参数实体类class
         * */
        EasyExcel.write(filename, ExcelBlackInfoVO.class).sheet("黑名单").doWrite(getExcelBlackInfoVOList());
    }
    public List<ExcelBlackInfoVO> getExcelBlackInfoVOList(){
        List<ExcelBlackInfoVO> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            ExcelBlackInfoVO blackInfoVO=new ExcelBlackInfoVO();
            blackInfoVO.setUsername("b"+i);
            blackInfoVO.setIdentityCard("111");
            blackInfoVO.setPhone("111");
            blackInfoVO.setTypeInfo("演员");
            blackInfoVO.setSex(Math.random()<0.5?"男":"女");
            list.add(blackInfoVO);
        }
        return list;
    }
    @Test
    public void ExcelReadTest() {
        //实现Excel读操作

        //设置读取文件夹地址和excel文件名称
        String filename="C:\\Users\\Lenovo\\Desktop\\excelwrite.xlsx";
        String filename2="C:\\Users\\Lenovo\\Desktop\\blackList.xls";
//        EasyExcel.read(filename,ExcelBlackInfoVO.class,new ExcelListener()).sheet().doRead();
        EasyExcel.read(filename2,ExcelBlackInfoVO.class,new ExcelListener()).sheet().doRead();
    }

    @Test
    public void containTest(){
        List<SportAndValue> quan1=gymOrderService.getTypeAndValue("303050b2-2c89-4cbf-899f-cc7af8cedadb");
        for(SportAndValue s:quan1){
            System.out.print(s.toString()+"\n");
        }
    }

    @Test
    public void getGymByName(){
        List<Gym> gymList=gymService.getByName("杭州电子科技大学游泳馆");
        for(Gym s:gymList){
            System.out.print(s.toString()+"\n");
        }
    }
    @Test
    public void dataTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());					//放入Date类型数据
        calendar.get(Calendar.YEAR);					//获取年份
        calendar.get(Calendar.MONTH);					//获取月份
        calendar.get(Calendar.DATE);					//获取日
        calendar.get(Calendar.HOUR);					//时（12小时制）
        calendar.get(Calendar.HOUR_OF_DAY);				//时（24小时制）
        calendar.get(Calendar.MINUTE);					//分
        calendar.get(Calendar.SECOND);					//秒
        calendar.get(Calendar.DAY_OF_WEEK);
        System.out.print(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.print(calendar.get(Calendar.HOUR_OF_DAY));
    }
    @Test
    public void dataTest2() throws ParseException {
        Date today=new Date();
        System.out.print(today);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today1=df.format(today);
        Date date=df2.parse(today1);
        System.out.print(date);
    }
    @Test
    public void dataTest3() throws ParseException {
        Date today=new Date();
        Date fiveDaysAgo= TimeUtile.stepDay(today,-5);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print(df.format(today)+"\n");
        System.out.print(df.format(fiveDaysAgo));
    }
    @Test
    public void dataTest4() throws ParseException {
        Date today=new Date();
        Date today1=TimeUtile.stepDay(today,1);
        Date today2=TimeUtile.stepDay(today,2);
        Date today3=TimeUtile.stepDay(today,4);
        int day=TimeUtile.getWeekDay(today);
        int day1=TimeUtile.getWeekDay(today1);
        int day2=TimeUtile.getWeekDay(today2);
        int day3=TimeUtile.getWeekDay(today3);
        System.out.print(day);
        System.out.print(day1);
        System.out.print(day2);
        System.out.print(day3);
    }
    @Test
    public void dataTest5() throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date d1=df.parse("11:15");
        Date d2=df.parse("24:0");
        Date d3=df.parse("10:10");
        float differ=(float)(d2.getTime()-d1.getTime())/3600000;
        float differ2=(float)Math.abs(d2.getTime()-d1.getTime())/3600000;
        System.out.print(differ+"h\n");
        System.out.print(differ2+"h\n");
        System.out.print(d2.getTime()>d3.getTime());
        System.out.print(d2.getTime()>d1.getTime());
        System.out.print(d1.getTime()>d3.getTime());
    }
    @Test
    public void dataTest6() {
        String weekend="9:00-11:30 13:30-19:00";
        String h5=weekend.substring(0,weekend.indexOf("-"));
        String h6=weekend.substring(weekend.indexOf("-")+1,weekend.indexOf(" "));
        weekend=weekend.substring(weekend.indexOf(" ")+1);//获取下半场
        String h7=weekend.substring(0,weekend.indexOf("-"));
        String h8=weekend.substring(weekend.indexOf("-")+1);
        System.out.print(h5+"\n");
        System.out.print(h6+"\n");
        System.out.print(h7+"\n");
        System.out.print(h8+"\n");
    }
    @Test
    public void test7() throws ParseException {
        Date date=new Date();
        DateFormat df = new SimpleDateFormat("yy-MM-dd");
        DateFormat df2 = new SimpleDateFormat("MM-dd");
        Date d1=df.parse("2022-10-13 00:20:00");
        Date d2=df.parse("2022-10-13 10:00:00");
//        System.out.print(d1.getTime()==d2.getTime());
        System.out.print(df2.format(date).replace("-","."));
    }
}
