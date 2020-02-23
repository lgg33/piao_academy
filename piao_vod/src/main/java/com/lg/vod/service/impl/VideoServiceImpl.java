package com.lg.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.lg.common.exception.PiaoException;
import com.lg.vod.service.VideoService;
import com.lg.vod.utils.AliyunVodSDKUtils;
import com.lg.vod.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: VideoServiceImpl
 * @date: 2020/2/8 20:37
 * @since JDK 1.8
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, filename, inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                System.out.println(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new PiaoException(30001, errorMessage);
                }
            }

            return videoId;
        } catch (Exception e) {
            throw new PiaoException(30001, "vod上传失败");
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println("RequestId = " + response.getRequestId());
        } catch (Exception e) {
            throw new PiaoException(30001, "删除视频失败");
        }
    }

    @Override
    public void deleteVideoS(List<String> VideoIds) {
        try {
            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建请求对象
            //一次只能批量删20个
            String str = org.apache.commons.lang.StringUtils.join(VideoIds.toArray(), ",");
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(str);

            //获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (Exception e) {
            throw new PiaoException(30001, "视频删除失败");
        }
    }
}
