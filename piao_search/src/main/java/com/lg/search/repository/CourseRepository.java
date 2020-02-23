package com.lg.search.repository;

import com.lg.search.entity.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseRepository
 * @date: 2020/2/23 17:01
 * @since JDK 1.8
 */
public interface CourseRepository extends ElasticsearchRepository<Course, String> {
}
