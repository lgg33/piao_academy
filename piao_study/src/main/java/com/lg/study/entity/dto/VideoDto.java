package com.lg.study.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author L
 * @version 1.0
 * @ClassName: VideoDto
 * @date: 2020/1/14 21:06
 * @since JDK 1.8
 */
@ApiModel("章节信息")
@Data
public class VideoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean isFree;
    private String videoSourceId;
}
