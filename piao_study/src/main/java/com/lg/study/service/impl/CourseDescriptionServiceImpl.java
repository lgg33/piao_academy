package com.lg.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.study.entity.CourseDescription;
import com.lg.study.mapper.CourseDescriptionMapper;
import com.lg.study.service.CourseDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
