package com.lg.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.common.response.ResponseResult;
import com.lg.common.response.ResultCode;
import com.lg.common.exception.PiaoException;
import com.lg.edu.entity.Teacher;
import com.lg.edu.query.TeacherQuery;
import com.lg.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    //{"code":20000,"data":{"token":"admin"}}
    //模拟登陆
    @PostMapping("/login")
    public ResponseResult login() {
        return ResponseResult.success().data("token","admin");
    }

    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    @GetMapping("/info")
    public ResponseResult info() {
        return ResponseResult.success().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation("查询所有教师")
    @GetMapping("/")
    public ResponseResult findAll() {
        List<Teacher> teachers = teacherService.list(null);
        return ResponseResult.success().data("item", teachers);
    }

    @ApiOperation("根据Id删除教师")
    @DeleteMapping("/{id}")
    private ResponseResult delete(@PathVariable("id") String id) {
        teacherService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation("分页查询教师")
    @GetMapping("/findList/{num}/{size}")
    public ResponseResult findList(@PathVariable("num") Integer num, @PathVariable("size") Integer size) {
        if(num <= 0 || size <= 0){
            throw new PiaoException(ResultCode.PARAM_ERROR);
        }
        Page<Teacher> page = new Page<>(num, size);
        teacherService.page(page, null);
        return ResponseResult.success().data("item", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("分页条件查询教师")
    @PostMapping("/findPageBy/{num}/{size}")
    public ResponseResult findPageBy(@PathVariable("num") Integer num, @PathVariable("size") Integer size,
                                     @RequestBody(required = false)TeacherQuery teacherQuery){
        Page<Teacher> page = new Page<>(num, size);
        teacherService.pageQuryBy(page, teacherQuery);
        return ResponseResult.success().data("item", page.getRecords()).data("total", page.getTotal());
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

    @ApiOperation("根据Id查询教师")
    @GetMapping("/{id}")
    public ResponseResult findById(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        return ResponseResult.success().data("item", teacher);
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