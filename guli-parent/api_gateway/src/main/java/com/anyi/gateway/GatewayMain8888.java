package com.anyi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

/**
 * @author 安逸i
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain8888 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain8888.class, args);
    }
}
