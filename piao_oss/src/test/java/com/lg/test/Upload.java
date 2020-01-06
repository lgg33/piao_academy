package com.lg.test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

/**
 * @author L
 * @version 1.0
 * @ClassName: Upload
 * @date: 2020/1/3 15:33
 * @since JDK 1.8
 */
public class Upload {

    @Test
    void test01() {
        String endPoint = "oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4Fgzc3uNLBPuj9nTrxmQ";
        String accessKeySecret = "wIaN0vd31W9tcuYmNl6rEVgiKHXhyg";
        String bucketName = "lg-study";


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        String content = "Hello OSS";
        ossClient.putObject(bucketName, "1.txt", new ByteArrayInputStream(content.getBytes()));
    }
}
