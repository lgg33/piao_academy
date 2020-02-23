package com.lg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.common.exception.PiaoException;
import com.lg.edu.entity.Video;
import com.lg.edu.entity.form.VideoInfoForm;
import com.lg.edu.feign.VideoFeignClient;
import com.lg.edu.mapper.VideoMapper;
import com.lg.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    VideoFeignClient videoFeignClient;

    @Override
    public boolean deleteByCourseId(String id) {
        //根据课程id查询所有视频列表
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        queryWrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(queryWrapper);

        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }

        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            videoFeignClient.deleteBatch(videoSourceIdList);
        }

        QueryWrapper<Video> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", id);
        return this.remove(queryWrapper2);
    }

    @Override
    public boolean getCountById(String id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void addVideoInfo(VideoInfoForm videoInfoForm) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.save(video);
        if (!result) {
            throw new PiaoException(30001, "课时信息保存失败");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        //从video表中取数据
        Video video = this.getById(id);
        if(video == null){
            throw new PiaoException(30001, "数据不存在");
        }
        //创建videoInfoForm对象
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(video, videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        //保存课时基本信息
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.updateById(video);
        if(!result){
            throw new PiaoException(30001, "课时信息保存失败");
        }
    }

    @Override
    public boolean deleteById(String id) {

        //删除视频资源 TODO
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            videoFeignClient.deleteById(videoSourceId);
        }
        int result = baseMapper.deleteById(id);
        return result > 0;
    }
}
