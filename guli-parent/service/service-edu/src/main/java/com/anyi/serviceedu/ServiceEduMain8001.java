package com.anyi.serviceedu;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author 安逸i
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(value = "com.anyi")
@EnableFeignClients
@EnableDiscoveryClient
public class ServiceEduMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEduMain8001.class,args);
    }
}
