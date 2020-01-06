package com.lg.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.lg.common.exception.PiaoException;
import com.lg.common.response.ResultCode;
import com.lg.oss.service.FileService;
import com.lg.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author L
 * @version 1.0
 * @ClassName: FileServiceImpl
 * @date: 2020/1/3 15:23
 * @since JDK 1.8
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public Map<String, String> upload(MultipartFile file) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        String fileDomain = ConstantPropertiesUtil.FILE_DOMAIN;

        HashMap<String, String> hashMap = new HashMap<>();
        String uploadUrl = null;
        String myDomain = null;

        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            assert original != null;
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = fileHost + "/" + filePath + "/" + newName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            myDomain = "http://" + fileDomain + "/" + fileUrl;
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
            hashMap.put("myDomain", myDomain);
            hashMap.put("uploadUrl", uploadUrl);
        } catch (IOException e) {
            throw new PiaoException(ResultCode.FILE_UPLOAD_ERROR);
        }
        return hashMap;
    }
}
