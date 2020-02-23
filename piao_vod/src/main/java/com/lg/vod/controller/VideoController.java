package com.lg.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lg.common.response.ResponseResult;
import com.lg.vod.service.VideoService;
import com.lg.vod.utils.AliyunVodSDKUtils;
import com.lg.vod.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: VideoController
 * @date: 2020/2/8 20:38
 * @since JDK 1.8
 */
@Api(tags = "视频上传服务")
@CrossOrigin
@RestController
@RequestMapping("/vod/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @ApiOperation("上传视频")
    @PostMapping("/upload")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile file) {
        String videoId = videoService.uploadVideo(file);
        return ResponseResult.success().data("item", videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/{id}")
    public ResponseResult deleteVideoById(@PathVariable("id") String id) {
        videoService.deleteVideo(id);
        return ResponseResult.success().message("删除视频成功");
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("delete-batch")
    public ResponseResult removeVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        videoService.deleteVideoS(videoIdList);
        return ResponseResult.success().message("视频删除成功");
    }

    @GetMapping("/{videoId}")
    public ResponseResult getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return ResponseResult.success().message("获取凭证成功").data("item", playAuth);
    }
}
