package com.lg.edu.mapper;

import com.lg.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lg.edu.entity.dto.CoursePublishDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishDto findCoursePublishById(String id);
}
