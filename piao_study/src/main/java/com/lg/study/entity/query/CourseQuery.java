package com.lg.study.entity.query;

import lombok.Data;

import java.util.Date;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseQuery
 * @date: 2020/2/23 21:08
 * @since JDK 1.8
 */
@Data
public class CourseQuery {
    private String id;
    private String title;
    private Double price;
    private Long viewCount;
    private Date gmtCreate;
    private String subjectId;
}
