package com.lg.study.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author L
 * @version 1.0
 * @ClassName: MyBatisPlusConfiguration
 * @date: 2019/12/30 10:30
 * @since JDK 1.8
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.lg.study.mapper")
public class MyBatisPlusConfiguration {
    
    @Bean
    public ISqlInjector iSqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
