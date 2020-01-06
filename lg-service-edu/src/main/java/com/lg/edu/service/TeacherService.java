package com.lg.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.edu.query.TeacherQuery;

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
}
