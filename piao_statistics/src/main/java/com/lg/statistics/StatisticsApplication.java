package com.lg.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L
 * @version 1.0
 * @ClassName: StatisticsApplciation
 * @date: 2020/2/11 9:38
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lg.statistics", "com.lg.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
