package com.lg.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.common.exception.PiaoException;
import com.lg.common.utils.PriceConstants;
import com.lg.study.entity.Course;
import com.lg.study.entity.CourseDescription;
import com.lg.study.entity.dto.CourseWebDto;
import com.lg.study.entity.form.CourseInfoForm;
import com.lg.study.entity.query.CourseQuery;
import com.lg.study.mapper.CourseMapper;
import com.lg.study.service.ChapterService;
import com.lg.study.service.CourseDescriptionService;
import com.lg.study.service.CourseService;
import com.lg.study.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;


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
    public List<Course> selectByTeacherId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Transactional
    @Override
    public CourseWebDto selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

    @Override
    public List<CourseQuery> toElasticsearch() {
        List<Course> courses = this.list(null);
        ArrayList<CourseQuery> courseQueries = new ArrayList<>();
        for (Course course : courses) {
            CourseQuery courseQuery = new CourseQuery();
            BeanUtils.copyProperties(course, courseQuery);
            courseQuery.setPrice(course.getPrice().doubleValue());
            courseQueries.add(courseQuery);
        }
        return courseQueries;
    }
}
