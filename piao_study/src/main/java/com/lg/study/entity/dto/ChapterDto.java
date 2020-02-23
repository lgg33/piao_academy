package com.lg.study.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: ChapterDto
 * @date: 2020/1/14 21:06
 * @since JDK 1.8
 */
@ApiModel("章节信息")
@Data
public class ChapterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private List<VideoDto> children;
}
