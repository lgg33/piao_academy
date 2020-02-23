package com.lg.study.entity.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author L
 * @version 1.0
 * @ClassName: CourseInfoForm
 * @date: 2020/1/10 10:18
 * @since JDK 1.8
 */
@Data
public class CourseInfoForm {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "分类ID")
    private String subjectId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程封面图片路径(域名)")
    private String coverBak;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
}

