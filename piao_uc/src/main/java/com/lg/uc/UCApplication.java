package com.lg.uc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L
 * @version 1.0
 * @ClassName: UCApplication
 * @date: 2020/2/11 8:58
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lg.uc", "com.lg.common"})
@EnableDiscoveryClient
public class UCApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCApplication.class, args);
    }
}
