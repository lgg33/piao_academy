package com.lg.edu.service.impl;

import com.lg.edu.entity.Video;
import com.lg.edu.mapper.VideoMapper;
import com.lg.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
