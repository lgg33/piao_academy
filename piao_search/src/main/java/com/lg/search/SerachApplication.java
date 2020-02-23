package com.lg.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author L
 * @version 1.0
 * @ClassName: SerachApplication
 * @date: 2020/2/23 15:28
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SerachApplication {
    public static void main(String[] args) {
        SpringApplication.run(SerachApplication.class, args);
    }
}
