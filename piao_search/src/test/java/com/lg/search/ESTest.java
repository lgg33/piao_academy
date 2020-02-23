package com.lg.search;

import com.alibaba.fastjson.JSON;
import com.lg.common.response.ResponseResult;
import com.lg.search.entity.Course;
import com.lg.search.fegin.CourseFeignClient;
import com.lg.search.repository.CourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: ESTest
 * @date: 2020/2/23 16:49
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseFeignClient courseFeignClient;

    @Test
    public void test01() {
        elasticsearchTemplate.createIndex(Course.class);
        elasticsearchTemplate.putMapping(Course.class);
    }

    @Test
    public void test02() {
        ResponseResult responseResult = courseFeignClient.toElasticsearch();
        String str = JSON.toJSONString(responseResult.getData().get("items"));
        List<Course> courses = JSON.parseArray(str, Course.class);
        System.out.println(courses);
        courseRepository.saveAll(courses);
    }

}
