package com.lg.edu.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author L
 * @version 1.0
 * @ClassName: CoursePublishDto
 * @date: 2020/2/3 18:40
 * @since JDK 1.8
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectFirst;
    private String subjectSecond;
    private String teacherName;
    private String price;//只用于显示
}
