package com.lg.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.study.entity.Teacher;
import com.lg.study.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lg
 * @since 2019-12-30
 */
public interface TeacherService extends IService<Teacher> {
    void pageQuryBy(Page<Teacher> page, TeacherQuery teacherQuery);

    Map<String, Object> byPageList(Page<Teacher> pageParam);

}
