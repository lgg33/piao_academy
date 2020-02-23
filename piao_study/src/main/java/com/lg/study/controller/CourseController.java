package com.lg.study.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.common.response.ResponseResult;
import com.lg.study.entity.Course;
import com.lg.study.entity.dto.ChapterDto;
import com.lg.study.entity.dto.CourseWebDto;
import com.lg.study.entity.form.CourseInfoForm;
import com.lg.study.entity.query.CourseQuery;
import com.lg.study.service.ChapterService;
import com.lg.study.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-01-10
 */
@Api(tags = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/study/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    ChapterService chapterService;


    @ApiOperation("根据Id查询课程信息")
    @GetMapping("/info/{id}")
    public ResponseResult findById(@PathVariable("id") String id) {
        CourseInfoForm courseInfoForm = courseService.findById(id);
        return ResponseResult.success().data("item", courseInfoForm);
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "/{num}/{size}")
    public ResponseResult pageList(
            @ApiParam(name = "num", value = "当前页码", required = true)
            @PathVariable("num") Long num,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable("size") Long size){

        Page<Course> pageParam = new Page<>(num, size);

        Map<String, Object> map = courseService.pageListWeb(pageParam);

        return  ResponseResult.success().data(map);
    }


    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "/{courseId}")
    public ResponseResult getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable("courseId") String courseId){

        //查询课程信息和讲师信息
        CourseWebDto courseWebDto = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterDto> chapterDtoList = chapterService.chapterDroList(courseId);

        return ResponseResult.success().data("course", courseWebDto).data("chapterDtoList", chapterDtoList);
    }

    @ApiOperation("获得课程查询条件")
    @GetMapping("/elasticsearch")
    public ResponseResult toElasticsearch() {
        List<CourseQuery> courseQueries = courseService.toElasticsearch();
        return ResponseResult.success().data("items", courseQueries);
    }

}

