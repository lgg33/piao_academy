package com.lg.edu.service;

import com.lg.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
