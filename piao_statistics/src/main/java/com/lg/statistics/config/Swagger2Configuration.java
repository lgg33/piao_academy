package com.lg.statistics.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author L
 * @version 1.0
 * @ClassName: Swagger2Configuration
 * @date: 2019/12/30 11:13
 * @since JDK 1.8
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

//    @Bean
//    public Docket adminApiConfig(){
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("adminApi")
//                .apiInfo(adminApiInfo())
//                .select()
//                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
//                .build();
//
//    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-统计中心API文档")
                .description("本文档描述了统计中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("lgg", "http://ganggangluo.com", "1575543408@qq.com"))
                .build();
    }

//    private ApiInfo adminApiInfo(){
//
//        return new ApiInfoBuilder()
//                .title("后台管理系统-课程中心API文档")
//                .description("本文档描述了后台管理系统课程中心微服务接口定义")
//                .version("1.0")
//                .contact(new Contact("lgg", "http://ganggangluo.com", "1575543408@qq.com"))
//                .build();
//    }
}
