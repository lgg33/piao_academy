package com.lg.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.common.response.ResponseResult;
import com.lg.edu.entity.Course;
import com.lg.edu.entity.form.CourseInfoForm;
import com.lg.edu.query.CourseQuery;
import com.lg.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @ApiOperation("添加课程")
    @PostMapping("/")
    public ResponseResult addCourse(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourse(courseInfoForm);
        if (!StringUtils.isEmpty(courseId)) {
            return ResponseResult.success().data("item", courseId);
        }
        return ResponseResult.error().message("保存失败");
    }

    @ApiOperation("根据Id查询课程信息")
    @GetMapping("/info/{id}")
    public ResponseResult findById(@PathVariable("id") String id) {
        CourseInfoForm courseInfoForm = courseService.findById(id);
        return ResponseResult.success().data("item", courseInfoForm);
    }

    @ApiOperation("根据Id修改课程信息")
    @PutMapping("/info")
    public ResponseResult updateById(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.updateCourseInfoById(courseInfoForm);
        return ResponseResult.success().data("item", courseId);
    }

    @ApiOperation("分页条件查询课程")
    @PostMapping("/findPageBy/{num}/{size}")
    public ResponseResult findPageBy(@PathVariable("num") Integer num, @PathVariable("size") Integer size,
                                     @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> page = new Page<>(num, size);
        courseService.queryByPage(page, courseQuery);
        return ResponseResult.success().data("items", page.getRecords()).data("total", page.getTotal());
    }

}

