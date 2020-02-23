package com.lg.search.service.impl;

import com.lg.search.fegin.CourseFeignClient;
import com.lg.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseServiceImpl
 * @date: 2020/2/23 20:57
 * @since JDK 1.8
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    CourseFeignClient courseFeignClient;


}
