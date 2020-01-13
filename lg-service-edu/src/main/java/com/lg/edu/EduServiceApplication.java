package com.lg.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L
 * @version 1.0
 * @ClassName: EduServiceApplication
 * @date: 2019/12/30 10:01
 * @since JDK 1.8
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lg.edu", "com.lg.common"})
public class EduServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduServiceApplication.class, args);
    }
}
