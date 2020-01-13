package com.lg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.common.exception.PiaoException;
import com.lg.common.utils.PriceConstants;
import com.lg.edu.entity.Course;
import com.lg.edu.entity.CourseDescription;
import com.lg.edu.entity.form.CourseInfoForm;
import com.lg.edu.mapper.CourseMapper;
import com.lg.edu.query.CourseQuery;
import com.lg.edu.service.CourseDescriptionService;
import com.lg.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Transactional
    @Override
    public String saveCourse(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean flag = this.save(course);
        if (!flag) {
            throw new PiaoException(30001, "保存课程信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        boolean flag2 = courseDescriptionService.save(courseDescription);
        if (!flag2) {
            throw  new PiaoException(30001, "保存课程详细信息失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoForm findById(String id) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        Course course = this.getById(id);
        if (course == null) {
            throw new PiaoException(30001, "数据不存在");
        }
        BeanUtils.copyProperties(course, courseInfoForm);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if (courseDescription == null) {
            throw new PiaoException(30001, "数据不存在");
        }
        courseInfoForm.setDescription(courseDescription.getDescription());
        courseInfoForm.setPrice(courseInfoForm.getPrice()
                .setScale(PriceConstants.DISPLAY_SCALE, BigDecimal.ROUND_FLOOR));
        return courseInfoForm;
    }

    @Override
    public String updateCourseInfoById(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean flag = this.updateById(course);
        if (!flag) {
            throw new PiaoException(30001, "保存课程信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        boolean flag2 = courseDescriptionService.updateById(courseDescription);
        if (!flag2) {
            throw new PiaoException(30001, "保存课程详情信息失败");
        }
        return course.getId();
    }

    @Override
    public void queryByPage(Page<Course> coursePage, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(coursePage, null);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(coursePage, queryWrapper);
    }
}
