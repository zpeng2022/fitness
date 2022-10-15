package com.yiie.utils;

import com.csvreader.CsvReader;
import com.yiie.vo.data.ExcelBlackInfoVO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/a_graduated_1?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8",
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
    public void insertDataByEntity(ExcelBlackInfoVO excelBlackInfoVO) throws IOException, SQLException {

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

            String name="id,identityCard,username,phone,typeInfo,sex,deleted";
            String id=UUID.randomUUID().toString();
            String identityCard=excelBlackInfoVO.getIdentityCard();
            String username=excelBlackInfoVO.getUsername();
            String phone=excelBlackInfoVO.getPhone();
            String typeInfo=excelBlackInfoVO.getTypeInfo();
            int sex=excelBlackInfoVO.getSex()=="男"?1:0;
            int deleted=0;
            String tmp="insert into blacklists("+name+")values("+"'"+id+"'"+","+"'"+identityCard+"'"+","+"'"+username+"'"+","+"'"+phone+"'"+","+"'"+typeInfo+"'"+","+"'"+sex+"'"+","+"'"+deleted+"'"+")";
            System.out.println("执行sql:"+tmp+"\n");
            s.execute(tmp); //执行插入
            s.close();

        }catch (SQLException e){
            e.printStackTrace();
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
}
