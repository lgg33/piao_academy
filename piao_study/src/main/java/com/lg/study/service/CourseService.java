package com.lg.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.study.entity.Course;
import com.lg.study.entity.dto.CourseWebDto;
import com.lg.study.entity.form.CourseInfoForm;
import com.lg.study.entity.query.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
public interface CourseService extends IService<Course> {

    CourseInfoForm findById(String id);

    List<Course> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebDto selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

    List<CourseQuery> toElasticsearch();
}
