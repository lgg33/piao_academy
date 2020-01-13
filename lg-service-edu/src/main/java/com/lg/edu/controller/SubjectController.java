package com.lg.edu.controller;


import com.lg.common.response.ResponseResult;
import com.lg.common.response.ResultCode;
import com.lg.edu.entity.Subject;
import com.lg.edu.entity.dto.SubjectDtoFirst;
import com.lg.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-01-06
 */
@Api(tags = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @ApiOperation("导入Excel")
    @PostMapping("/import")
    public ResponseResult importExcel(@RequestParam("file") MultipartFile file) {
        List<String> msg = subjectService.batchImport(file);
        if(msg.size() == 0){
            return ResponseResult.success().message("批量导入成功");
        }else{
            return ResponseResult.error().message("部分数据导入失败").data("messageList", msg);
        }
    }

    @ApiOperation("课程嵌套数据列表")
    @GetMapping("/")
    public ResponseResult subjectNest() {
        List<SubjectDtoFirst> list = subjectService.nestedList();
        return ResponseResult.success().data("items", list);
    }

    @ApiOperation("添加一级课程分类数据")
    @PostMapping("/addSubjectFirst")
    public ResponseResult addSubject(@RequestBody Subject subject) {
        boolean flag = subjectService.saveFirst(subject);
        if (flag) {
            return ResponseResult.success();
        }
        return ResponseResult.setResult(ResultCode.SUBJECT_ADD_FAIL);
    }

    @ApiOperation("添加二级课程分类数据")
    @PostMapping("/addSubjectSecond")
    public ResponseResult addSubjectSecond(@RequestBody Subject subject) {
        boolean flag = subjectService.saveSecond(subject);
        if (flag) {
            return ResponseResult.success();
        }
        return ResponseResult.setResult(ResultCode.SUBJECT_ADD_FAIL);
    }

    @ApiOperation("根据Id删除课程分类数据")
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") String id) {
        boolean flag = subjectService.deleteById(id);
        if (flag) {
            return ResponseResult.success();
        }
        return ResponseResult.setResult(ResultCode.SUBJECT_DELETE_FAIL);
    }
}

