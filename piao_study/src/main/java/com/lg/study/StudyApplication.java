package com.lg.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L
 * @version 1.0
 * @ClassName: StudyApplication
 * @date: 2020/2/11 20:49
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lg.study", "com.lg.common"})
@EnableDiscoveryClient
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }
}
