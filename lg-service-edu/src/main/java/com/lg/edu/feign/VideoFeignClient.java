package com.lg.edu.feign;

import com.lg.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: VideoFeignClient
 * @date: 2020/2/10 20:00
 * @since JDK 1.8
 */
@FeignClient(name = "lg-vod")
@Component
public interface VideoFeignClient {

    @DeleteMapping("/admin/vod/video/{id}")
    ResponseResult deleteById(@PathVariable("id") String id);

    @DeleteMapping("admin/vod/video/delete-batch")
    ResponseResult deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
