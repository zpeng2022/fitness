package com.yiie.utils;

import com.csvreader.CsvReader;
import com.yiie.vo.data.ExcelBlackInfoVO;
import com.yiie.vo.data.PhysicalInfoVo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ssc {
    //前端上传CSV文件后生成路径，路径名传给该函数
    public static void readCSVPredict(String path) throws IOException, SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigman_yt?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");
                Statement s = c.createStatement();
        )
        {
            CsvReader reader=new CsvReader(path,',',Charset.forName("utf-8"));
            String name="ship_id,latitude,longitude";
            //name就是表格列的名称
            reader.readHeaders();
            int len=reader.getHeaders().length;

            //len表示的是有几个列
            while(reader.readRecord()){  //整个while就是为了组装成为 插入语句的形式 更换掉id中的_
//                MyPrint.print(reader.get(0));
                String tmp="insert into trail_predict("+name+")values("+"'"+reader.get(0).replaceFirst("_","")+"'";
//                System.out.println(tmp);
                for(int i=1;i<len-1;i++){
                    tmp+=","+"'"+reader.get(i).replaceAll("'", "\\\\'")+"'";
                }
                tmp+=","+"'"+reader.get(len-1).replaceAll("'", "\\\\'")+"');";
                System.out.println(tmp);
                //tmp就是组装好的插入语句，即insert into talble(属性）values（内容）；
                s.execute(tmp); //执行插入
            }
            reader.close();
            s.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //前端上传CSV文件后生成路径，路径名传给该函数
    public static String readCSV(String path) throws IOException, SQLException {
        String stype="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/a_graduated_1?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");
                Statement s = c.createStatement();
        )
        {
            CsvReader reader=new CsvReader(path,',',Charset.forName("utf-8"));
            String name="ship_id,latitude,longitude,speed,direction,time,type";
            //name就是表格列的名称
            reader.readHeaders();
            int len=reader.getHeaders().length;

            //len表示的是有几个列
            int qu=0;
            while(reader.readRecord()){  //整个while就是为了组装成为 插入语句的形式 更换掉id中的_
//                MyPrint.print(reader.get(0));
                String tmp="insert into trail("+name+")values("+"'"+reader.get(0).replaceFirst("_","")+"'";
//                System.out.println(tmp);
                for(int i=1;i<len-1;i++){
                    tmp+=","+"'"+reader.get(i).replaceAll("'", "\\\\'")+"'";
                }
                tmp+=","+"'"+reader.get(len-1).replaceAll("'", "\\\\'")+"');";
//                System.out.println(tmp);
                if(qu==1){
                    int index=tmp.indexOf("网");
                    stype=tmp.substring(index-1,index);
                }
                qu++;
                //tmp就是组装好的插入语句，即insert into talble(属性）values（内容）；
                s.execute(tmp); //执行插入
            }
            reader.close();
            s.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return stype;
    }
    //前端上传CSV文件后生成路径，路径名传给该函数
    public static void readCSV2(String path) throws IOException, SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/a_graduated_1?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");
                Statement s = c.createStatement();
        )
        {
            CsvReader reader=new CsvReader(path,',',Charset.forName("utf-8"));
            String name="id,type,predict";
            //name就是表格列的名称
            reader.readHeaders();
            int len=reader.getHeaders().length;
            //len表示的是有几个列
            while(reader.readRecord()){  //整个while就是为了组装成为 插入语句的形式
                String tmp="insert into type_predict("+name+")values("+"'"+reader.get(0)+"'";
//                System.out.println(tmp);
                for(int i=1;i<len-1;i++){
                    tmp+=","+"'"+reader.get(i).replaceAll("'", "\\\\'")+"'";
                }
                tmp+=","+"'"+reader.get(len-1).replaceAll("'", "\\\\'")+"');";
                System.out.println(tmp);
                //tmp就是组装好的插入语句，即insert into talble(属性）values（内容）；
                s.execute(tmp); //执行插入
            }
            reader.close();
            s.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //前端上传CSV文件后生成路径，路径名传给该函数
    public static void readXSL(String path) throws IOException, SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigman?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");
                Statement s = c.createStatement();
        )
        {
            CsvReader reader=new CsvReader(path,',',Charset.forName("UTF-8"));

            String name="id,username,identityCard,typeInfo,phone,sex";
            //name就是表格列的名称
            reader.readHeaders();
            int len=reader.getHeaders().length;
            //len表示的是有几个列
            while(reader.readRecord()){  //整个while就是为了组装成为 插入语句的形式
                String id= UUID.randomUUID().toString();
                String tmp="insert into blacklists("+name+")values("+"'"+id+"'"+","+"'"+reader.get(0)+"'";
//                System.out.println(tmp);
                for(int i=1;i<len-1;i++){
                    tmp+=","+"'"+reader.get(i).replaceAll("'", "\\\\'")+"'";
                }
                tmp+=","+"'"+reader.get(len-1).replaceAll("'", "\\\\'")+"');";
                System.out.println(tmp);
                //tmp就是组装好的插入语句，即insert into talble(属性）values（内容）；
                s.execute(tmp); //执行插入
            }
            reader.close();
            s.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void insertDataByEntity(ExcelBlackInfoVO excelBlackInfoVO,String deptId) throws IOException, SQLException {
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("dm.jdbc.driver.DmDriver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try (
                /*Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigman_yt?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");*/
                //部署到远程用的连接
                /*Connection c = DriverManager.getConnection("jdbc:mysql://localhost/bigman_yt?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=GMT%2b8",
                        "root", "root");*/
                Connection c = DriverManager.getConnection("jdbc:dm://localhost:5238",
                        "SYSDBA", "SYSDBA");
                Statement s = c.createStatement();
        )
        {
            System.out.print("\n\n\nexcelBlackInfoVO:"+excelBlackInfoVO+"\n\n");


            String name="id,identityCard,username,deptID,phone,typeInfo,importTime,sex,deleted";
            String id=UUID.randomUUID().toString();
            String identityCard=excelBlackInfoVO.getIdentityCard();
            String username=excelBlackInfoVO.getUsername();
            String phone=excelBlackInfoVO.getPhone();
            String typeInfo=excelBlackInfoVO.getTypeInfo();
            if(typeInfo!=null){
                typeInfo=typeInfo.replaceAll(".0","");
            }
           /* if(typeInfo.length()>0){
                typeInfo=typeInfo.substring(0,1);//数据库中就一位，强制裁剪
            }*/
//            String deptId2=excelBlackInfoVO.getDeptId();//从excel中获取的
            Date d=new Date();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=df2.format(d);
            int sex;
            int deleted=1;
            if(name==null||identityCard==null||typeInfo==null||phone==null||excelBlackInfoVO.getSex()==null||deptId==null){
//                throw new IllegalArgumentException("deptId为空，不能添加");
            }
            else {
                String sex1=excelBlackInfoVO.getSex();
                if(sex1.equals("男"))
                    sex=1;
                else if(sex1.equals("女"))
                    sex=2;
                else
                    sex=0;
                String tmp="insert into blacklists("+name+")values("+"'"+id+"'"+","+"'"+identityCard+"'"+","+"'"+username+"'"+","+"'"+deptId+"'"+","+"'"+phone+"'"+","+"'"+typeInfo+"'"+","+"'"+date+"'"+","+"'"+sex+"'"+","+"'"+deleted+"'"+")";
                System.out.println("执行sql:"+tmp+"\n");
                s.execute(tmp); //执行插入
            }
            s.close();
        }

    }



    //test
    public static void main(String args[]) throws Exception{
        ExcelUtils excelUtils=new ExcelUtils();
        String path = new String("src/main/resources/data/196568_5.csv");
        String path2 = new String("C:\\Users\\Lenovo\\Desktop\\1965_5.csv");
        String path3 = new String("src/main/resources/data/all.csv");
        String path4 = new String("src/main/resources/data/test.csv");
        String path5 = new String("src/main/resources/data/test.xls");
        String path6 = new String("src/main/resources/data/blackList.xls");
        String pat6 = new String("C:\\Users\\Lenovo\\Desktop\\test.xls");
//        readCSV(path2);
//        readXSL(path4);
        File file=new File("C:\\Users\\Lenovo\\Desktop\\blackList.xls");
    }

    public void insertDataByEntity_Physical(PhysicalInfoVo physicalInfoVo) throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try (
                /*Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigman_yt?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
                        "root", "root");*/
                //部署到远程用的连接
                Connection c = DriverManager.getConnection("jdbc:mysql://123.60.165.20:13306/bigman_yt?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=GMT%2b8",
                        "root", "fitnessBooking_2022_hdu");
                Statement s = c.createStatement();
        )
        {
//            System.out.print("\n\n\nphysicalInfoVo:"+physicalInfoVo+"\n\n");
            String name="studentStatus,exempt,height,weight,vitalCapacity,runFifty,settingForward,skippingRope,leftVision,rightVision," +
                    "leftError,rightError,leftMirror,rightMirror,abdominalCurl,runBack,createTime,deleted";

            String studentStatus=physicalInfoVo.getStudentStatus();
            int exempt=physicalInfoVo.getExempt();
            String height=physicalInfoVo.getHeight();
            String weight=physicalInfoVo.getWeight();
            String vitalCapacity=physicalInfoVo.getVitalCapacity();
            String runFifty=physicalInfoVo.getRunFifty();
            String settingForward=physicalInfoVo.getSettingForward();
            String skippingRope=physicalInfoVo.getSkippingRope();
            String leftVision=physicalInfoVo.getLeftVision();
            String rightVision=physicalInfoVo.getRightVision();
            String leftError=physicalInfoVo.getLeftError();
            String rightError=physicalInfoVo.getRightError();
            String leftMirror=physicalInfoVo.getLeftMirror();
            String rightMirror=physicalInfoVo.getRightMirror();
            String abdominalCurl=physicalInfoVo.getAbdominalCurl();
            String runBack=physicalInfoVo.getRunBack();
            Date createTime=new Date();
            Integer deleted=1;
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=df2.format(createTime);
            String tmp="REPLACE into physicaltest("+name+") values("+"'"+studentStatus+"'"+","+"'"+exempt+"'"+","+"'"+height+"'"+","+"'"+weight+"'"+","+"'"+vitalCapacity+"'"+","+"'"+runFifty+"'"+","+"'"+settingForward+"'"+","+"'"+skippingRope+"'"+","+"'"+leftVision+"'"+","+"'"+rightVision+"'"+","+"'"+leftError+"'"+","+"'"+rightError+"'"+","+"'"+leftMirror+"'"+","+"'"+rightMirror+"'"+","+"'"+abdominalCurl+"'"+","+"'"+runBack+"'"+","+"'"+date+"'"+","+"'"+deleted+"'"+")";
            System.out.println("执行sql:"+tmp+"\n");
            s.execute(tmp); //执行插入
            s.close();
        }
    }
}
