package com.lg.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.common.exception.PiaoException;
import com.lg.study.entity.Chapter;
import com.lg.study.entity.Video;
import com.lg.study.entity.dto.ChapterDto;
import com.lg.study.entity.dto.VideoDto;
import com.lg.study.mapper.ChapterMapper;
import com.lg.study.service.ChapterService;
import com.lg.study.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    VideoService videoService;

    @Override
    public boolean deleteByCourseId(String id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        return this.remove(queryWrapper);
    }

    @Override
    public List<ChapterDto> chapterDroList(String courseId) {
        ArrayList<ChapterDto> chapterDtoList = new ArrayList<>();
        //章节查询条件
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapterList = this.list(queryWrapper);
        //小节查询条件
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("sort", "id");
        List<Video> videoList = videoService.list(videoQueryWrapper);
        //查询封装数据
        for (Chapter chapter : chapterList) {
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
            ArrayList<VideoDto> videoDtoList = new ArrayList<>();
            for (Video video : videoList) {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(video, videoDto);
                    videoDtoList.add(videoDto);
                }
            }
            chapterDto.setChildren(videoDtoList);
        }
        return chapterDtoList;
    }

    @Override
    public boolean deleteChapterById(String id) {
        if (videoService.getCountById(id)) {
            throw new PiaoException(30001, "该分章节下存在视频课程，请先删除视频课程");
        }
        return this.removeById(id);
    }
}
