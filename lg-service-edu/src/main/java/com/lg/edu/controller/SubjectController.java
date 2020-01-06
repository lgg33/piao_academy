package com.lg.edu.controller;


import com.lg.common.response.ResponseResult;
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
@Api("课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @ApiOperation("批量导入Excel")
    @PostMapping("/import")
    public ResponseResult importExcel(@RequestParam("file") MultipartFile file) {
        List<String> msg = subjectService.batchImport(file);
        if(msg.size() == 0){
            return ResponseResult.success().message("批量导入成功");
        }else{
            return ResponseResult.error().message("部分数据导入失败").data("messageList", msg);
        }
    }
}

