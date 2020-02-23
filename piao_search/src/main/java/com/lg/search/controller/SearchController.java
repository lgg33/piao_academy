package com.lg.search.controller;

import com.lg.common.response.ResponseResult;
import com.lg.search.fegin.CourseFeignClient;
import com.lg.search.service.SearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseController
 * @date: 2020/2/23 20:56
 * @since JDK 1.8
 */
@Api(tags = "查询服务")
@RestController
@CrossOrigin
@RequestMapping("search/course")
public class SearchController {

    @Autowired
    CourseFeignClient courseFeignClient;

    @Autowired
    SearchService courseSrevice;

    @GetMapping()
    public ResponseResult get() {
        return ResponseResult.success();
    }
}
