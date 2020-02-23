package com.lg.edu.controller;


import com.lg.common.response.ResponseResult;
import com.lg.edu.entity.form.VideoInfoForm;
import com.lg.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
@Api(tags = "课时管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("/")
    public ResponseResult addVideo(@RequestBody VideoInfoForm videoInfoForm) {
        videoService.addVideoInfo(videoInfoForm);
        return ResponseResult.success();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("/{id}")
    public ResponseResult getVideInfoById(@PathVariable("id") String id){
        VideoInfoForm videoInfoForm = videoService.getVideoInfoFormById(id);
        return ResponseResult.success().data("item", videoInfoForm);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("/")
    public ResponseResult updateCourseInfoById(@RequestBody VideoInfoForm videoInfoForm){
        videoService.updateVideoInfoById(videoInfoForm);
        return ResponseResult.success();
    }

    @ApiOperation("根据Id删除课时")
    @DeleteMapping("/{id}")
    public ResponseResult deleteVideoById(@PathVariable("id") String id) {
        boolean result = videoService.deleteById(id);
        if (result) {
            return ResponseResult.success();
        }
        return ResponseResult.error().message("删除失败");
    }
}

