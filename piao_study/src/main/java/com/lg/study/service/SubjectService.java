package com.lg.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.study.entity.Subject;
import com.lg.study.entity.dto.SubjectDtoFirst;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lg
 * @since 2020-01-06
 */
public interface SubjectService extends IService<Subject> {
    List<String> batchImport(MultipartFile file);

    List<SubjectDtoFirst> nestedList();

    boolean deleteById(String id);

    boolean saveFirst(Subject subject);

    boolean saveSecond(Subject subject);
}
