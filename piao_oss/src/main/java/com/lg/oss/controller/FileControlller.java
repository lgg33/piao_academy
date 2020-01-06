package com.lg.oss.controller;

import com.lg.common.response.ResponseResult;
import com.lg.oss.service.impl.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author L
 * @version 1.0
 * @ClassName: FileControlller
 * @date: 2020/1/3 15:47
 * @since JDK 1.8
 */
@Api("阿里云文件管理OSS")
@RestController
@RequestMapping("/admin/file")
@CrossOrigin
public class FileControlller {

    @Autowired
    FileServiceImpl fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) {
        Map<String, String> map = fileService.upload(file);
        return ResponseResult.success().message("上传成功").data("item", map);
    }

}
