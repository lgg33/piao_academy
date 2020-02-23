package com.lg.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L
 * @version 1.0
 * @ClassName: VodApplication
 * @date: 2020/2/8 20:23
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lg.vod", "com.lg.common"})
@EnableDiscoveryClient
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
