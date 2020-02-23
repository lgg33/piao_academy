package com.lg.study.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L
 * @version 1.0
 * @ClassName: SubjectDtoFirst
 * @date: 2020/1/8 16:18
 * @since JDK 1.8
 */
@Data
public class SubjectDtoFirst {
    private String id;//一级分类id
    private String title;//一级分类名称
    //一级分类所有的二级分类
    private List<SubjectDtoTwo> children = new ArrayList<>();
}
