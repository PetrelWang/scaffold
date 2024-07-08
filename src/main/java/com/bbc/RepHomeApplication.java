package com.bbc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//springboot的启动注解
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class},scanBasePackages = "com.bbc")
@MapperScan(basePackages = "com.bbc.mapper")
public class RepHomeApplication {
    public static void main(String[] args) {
        //启动
        SpringApplication.run(RepHomeApplication.class);
    }
}
