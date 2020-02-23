package com.lg.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lg.study.entity.Course;
import com.lg.study.entity.dto.CourseWebDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
public interface CourseMapper extends BaseMapper<Course> {
    CourseWebDto selectInfoWebById(String courseId);
}
