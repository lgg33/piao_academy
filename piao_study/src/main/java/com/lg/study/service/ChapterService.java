package com.lg.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.study.entity.Chapter;
import com.lg.study.entity.dto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lg
 * @since 2020-01-13
 */
public interface ChapterService extends IService<Chapter> {
    boolean deleteByCourseId(String id);

    List<ChapterDto> chapterDroList(String courseId);

    boolean deleteChapterById(String id);

}
