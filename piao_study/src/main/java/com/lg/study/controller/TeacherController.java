package com.lg.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.common.response.ResponseResult;
import com.lg.common.response.ResultCode;
import com.lg.study.entity.Course;
import com.lg.study.entity.Teacher;
import com.lg.study.service.CourseService;
import com.lg.study.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lg
 * @since 2019-12-30
 */
@Api(tags = "教师接口,提供教师信息的管理功能")
@RestController
@RequestMapping("/study/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseService courseService;

    @ApiOperation("查询所有教师")
    @GetMapping("/")
    public ResponseResult findAll() {
        List<Teacher> teachers = teacherService.list(null);
        return ResponseResult.success().data("items", teachers);
    }

    @ApiOperation("根据名称查询教师")
    @GetMapping("/getByName/{name}")
    public ResponseResult getByName(@PathVariable("name") String name) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Teacher teacher = teacherService.getOne(queryWrapper);
        if (teacher != null) {
            return ResponseResult.setResult(ResultCode.TEACHER_NAME_ALREADY_EXIST);
        }
        return ResponseResult.success();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "/{id}")
    public ResponseResult getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        Teacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList = courseService.selectByTeacherId(id);

        return ResponseResult.success().data("teacher", teacher).data("courseList", courseList);
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "/{num}/{size}")
    public ResponseResult pageList(
            @ApiParam(name = "num", value = "当前页码", required = true)
            @PathVariable("num") Long num,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable("size") Long size){

        Page<Teacher> pageParam = new Page<>(num, size);

        Map<String, Object> map = teacherService.byPageList(pageParam);

        return  ResponseResult.success().data(map);
    }

    @ApiOperation("添加教师")
    @PostMapping("/addTeacher")
    public ResponseResult addTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }

    @ApiOperation("根据Id修改教师")
    @PutMapping("/updateById")
    public ResponseResult updateById(@RequestBody Teacher teacher) {
        boolean update = teacherService.updateById(teacher);
        if (update) {
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }

}