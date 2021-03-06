package com.lg.edu.service;

import com.lg.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.edu.entity.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
public interface VideoService extends IService<Video> {

    boolean deleteByCourseId(String id);

    boolean getCountById(String id);


    void addVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    boolean deleteById(String id);
}
