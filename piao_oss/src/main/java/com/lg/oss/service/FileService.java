package com.lg.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author L
 * @version 1.0
 * @ClassName: FileService
 * @date: 2020/1/3 15:21
 * @since JDK 1.8
 */
public interface FileService {
    Map<String, String> upload(MultipartFile file);
}
