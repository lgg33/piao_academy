package com.lg.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: VideoService
 * @date: 2020/2/8 20:37
 * @since JDK 1.8
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    void deleteVideoS(List<String> VideoIds);
}
