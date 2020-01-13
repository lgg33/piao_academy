package com.lg.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.edu.entity.form.CourseInfoForm;
import com.lg.edu.query.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
public interface CourseService extends IService<Course> {

    String saveCourse(CourseInfoForm courseInfoForm);

    CourseInfoForm findById(String id);

    String updateCourseInfoById(CourseInfoForm courseInfoForm);

    void queryByPage(Page<Course> coursePage, CourseQuery courseQuery);
}
