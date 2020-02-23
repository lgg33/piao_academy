package com.lg.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

//阿里云视频点播sdk初始化操作
public class AliyunVodSDKUtils {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }
}
