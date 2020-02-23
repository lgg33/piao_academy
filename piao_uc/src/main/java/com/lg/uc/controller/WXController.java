package com.lg.uc.controller;

import com.google.gson.Gson;
import com.lg.common.exception.PiaoException;
import com.lg.uc.entity.Member;
import com.lg.uc.service.MemberService;
import com.lg.uc.utils.ConstantPropertiesUtil;
import com.lg.uc.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author L
 * @version 1.0
 * @ClassName: WXController
 * @date: 2020/2/13 14:16
 * @since JDK 1.8
 */
@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WXController {

    @Autowired
    MemberService memberService;

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new PiaoException(30001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "piaopiao";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    /**
     * 1、获取回调参数
     * 2、从redis中读取state进行比对，异常则拒绝调用
     * 3、向微信的授权服务器发起请求，使用临时票据换取access token
     * 4、使用上一步获取的openid查询数据库，判断当前用户是否已注册，如果已注册则直接进行登录操作
     * 5、如果未注册，则使用openid和access token向微信的资源服务器发起请求，请求获取微信的用户信息
     *   5.1、将获取到的用户信息存入数据库
     *   5.2、然后进行登录操作
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state){

        //得到授权临时票据code
        System.out.println(code);
        System.out.println(state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        String result;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new PiaoException(30001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        Member member = memberService.getByOpenid(openid);
        if(member == null){
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new PiaoException(30001, "获取用户信息失败");
            }

            //解析json
            HashMap mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //TODO 登录

        return "redirect:http://localhost:3000";
    }


}
