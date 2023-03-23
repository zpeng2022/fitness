package com.yiie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.print("-----------------MyPicConfig-----------------\n");
//        String path="D:\\研究生\\学习\\2022暑假项目\\预约平台项目\\整体项目\\2022917\\fitness\\src\\main\\resources\\static\\pic\\**";
        registry.addResourceHandler("/pic/**").
                addResourceLocations("file:"+ System.getProperty("user.dir")+"\\src\\main\\resources\\static\\pic\\",
                        "classpath:/static/","file:static/");
    }

    public static void main(String[] args) {
        System.out.print(System.getProperty("user.dir"));
    }
}
