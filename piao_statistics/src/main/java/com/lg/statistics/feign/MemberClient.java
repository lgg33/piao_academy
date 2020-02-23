package com.lg.statistics.feign;

import com.lg.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author L
 * @version 1.0
 * @ClassName: MemberClient
 * @date: 2020/2/11 9:47
 * @since JDK 1.8
 */
@FeignClient(name = "lg-uc")
@Component
public interface MemberClient {

    @GetMapping(value = "/admin/uc/member/{day}")
    ResponseResult registerCount(@PathVariable("day") String day);

}
