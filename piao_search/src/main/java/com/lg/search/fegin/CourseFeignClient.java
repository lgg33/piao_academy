package com.lg.search.fegin;

import com.lg.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseFeignClient
 * @date: 2020/2/23 20:58
 * @since JDK 1.8
 */
@FeignClient(name = "lg-study")
@Component
public interface CourseFeignClient {

    @GetMapping("/study/course/elasticsearch")
    ResponseResult toElasticsearch();

}
