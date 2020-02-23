package com.lg.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author L
 * @version 1.0
 * @ClassName: OSSAplication
 * @date: 2020/1/3 13:52
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class, args);
    }
}
