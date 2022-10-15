package com.yiie.utils;

import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.InputStream;

import jxl.write.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OSS {
    static String accessKeyId = "LTAI5tSjEKyAz6Z4fihMxHia";
    static String accessKeySecret = "";
    static String bucketName="bigman1718";
    static String endPoint="https://oss-cn-hangzhou.aliyuncs.com";
    static String endPoint2="oss-cn-hangzhou.aliyuncs.com";
    static String myBucketAddress="gymPicture";

    public void bookUpload(String filePath,String bucketAddress){
        File file=new File(filePath);
        com.aliyun.oss.OSS ossClient=new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, bucketAddress, file);
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
    public String gymPictureLoad(MultipartFile file) throws IOException {
        com.aliyun.oss.OSS ossClient=new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        InputStream inputStream= file.getInputStream();//注意导入包为java.io
//        String filename=file.getOriginalFilename();
        //防止有中文,直接用UUID存储,中文在阿里云的存储路径中会被替换成16进制，但要留下保存格式
        String filename=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        String uuid= UUID.randomUUID().toString().replaceAll("-","");//添加UUID防止重名图片
        filename=uuid+filename;
        //添加日期文件夹区分
        SimpleDateFormat sdf= new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath= sdf.format(new Date());
        System.out.print("当前日期:"+datePath+"\n");
        filename=datePath+filename;
        //再在最前面加上bucket中的文件夹区分
        filename=myBucketAddress+filename;

        System.out.print("最终存储:"+filename+"\n");
        try {
            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, bucketAddress, inputStream);
            //在bucket中的存储路径在第二个参数上进行修改，a/b/pic.jpg
            ossClient.putObject(bucketName,filename,inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        String picturePath="https://"+bucketName+"."+endPoint2+"/"+filename;
        return picturePath;
    }
}
