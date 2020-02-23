package com.lg.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author L
 * @version 1.0
 * @ClassName: Course
 * @date: 2020/2/23 16:38
 * @since JDK 1.8
 */
@Document(indexName = "course", type = "info", shards = 3, replicas = 2)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Long)
    private Long viewCount;
    @Field(type = FieldType.Date)
    private Date gmtCreate;
    @Field(type = FieldType.Keyword)
    private String subjectId;

}
