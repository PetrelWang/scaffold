package com.bbc.common;

/**
 * @Title: QwWeiXinUtil
 * @Author WangHaoWei
 * @Package com.bbc.common
 * @Date 2024/2/6 17:25
 * @description:
 */


import com.alibaba.fastjson.JSONObject;
import com.bbc.dto.WeiXinLoginDTO;
import com.bbc.dto.WeiXinUserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class QwWeiXinUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  WecomUtil wecomUtil;

    /**
     * 获取用户ID
     *
     * @param accessToken
     * @param code
     * @return
     */
    public  String getUserIDByToken(String accessToken, String code) throws IOException {
        //1.获取请求的url
        String url = wecomUtil.getUserIDUrl.replace("ACCESS_TOKEN", accessToken)
                .replace("CODE", code);
        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = SendRequest.sendGet(url);

        //3.错误消息处理
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            log.info("用户ID：" + jsonObject.getString("UserId"));
            log.info("OpenID：" + jsonObject.getString("OpenId"));
            if (!StringUtils.isEmpty(jsonObject.getString("UserId"))) {
                return jsonObject.getString("UserId");
            } else if (jsonObject.getString("OpenId") != null) {
                log.info("该用户不是本企业人员，OpenID为：" + jsonObject.getString("OpenId"));
                return null;
            }
        }
        return null;
    }

    /**
     * 初始加载二维码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public WeiXinLoginDTO loginGetErWeiMa() throws UnsupportedEncodingException {
        String redirect_uri = URLEncoder.encode(wecomUtil.redirectUri, "utf-8");
        WeiXinLoginDTO weiXinLoginDTO = new WeiXinLoginDTO();
        weiXinLoginDTO.setAppid(wecomUtil.appId);
        weiXinLoginDTO.setAgentid(wecomUtil.agentId);
        weiXinLoginDTO.setRedirectUri(redirect_uri);
        return weiXinLoginDTO;
    }

    /**
     * 获取token
     *
     * @return
     */
    public  String getToken() throws IOException {
        String token = getFirstAccessToken(wecomUtil.appId, wecomUtil.secret);
        stringRedisTemplate.opsForValue().set("qwChatToken", token, 100, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 获取token
     *
     * @param appid
     * @param appsecret
     * @return
     */
    public  String getFirstAccessToken(String appid, String appsecret) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String saveDate = sdf.format(new Date());
        log.info("获取微信TOKN请求开始，当前时间：" + saveDate);
        String requestUrl = wecomUtil.accessTokenUrl.replace("ID", appid)
                .replace("SECRET", appsecret);
        JSONObject jsonObject = SendRequest.sendGet(requestUrl);
        // 如果请求成功
        if (null != jsonObject && jsonObject.getIntValue("errcode") == 0) {
            try {
                log.info("获取的token: " + jsonObject.getString("access_token"));
                log.info("时间" + saveDate);
                return jsonObject.getString("access_token");
            } catch (Exception e) {
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getString("errmsg"));
            }
        } else {
            log.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getString("errmsg"));
        }
        return null;
    }

    /**
     * 获取用户
     *
     * @param accessToken
     * @param userId
     * @return
     */
    public  WeiXinUserInfoDTO getUserInfoByID(String accessToken, String userId) throws IOException {
        //1.获取请求的url
        String url = wecomUtil.getUserUrl.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userId);
        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = SendRequest.sendGet(url);

        //3.错误消息处理
        if (null != jsonObject && 0 != jsonObject.getIntValue("errcode")) {
            log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
        } else {
            log.info("用户ID：" + jsonObject.getString("userid"));
            log.info("用户名称：" + jsonObject.getString("name"));
            log.info("用户手机号：" + jsonObject.getString("mobile"));
            log.info("用户邮箱：" + jsonObject.getString("email"));
            WeiXinUserInfoDTO weiXinUserInfoDTO = new WeiXinUserInfoDTO();
            weiXinUserInfoDTO.setUserid(jsonObject.getString("userid"));
            weiXinUserInfoDTO.setName(jsonObject.getString("name"));
            weiXinUserInfoDTO.setMobile(jsonObject.getString("mobile"));
            weiXinUserInfoDTO.setEmail(jsonObject.getString("email"));
            weiXinUserInfoDTO.setAvatar(jsonObject.getString("avatar"));//头像
            weiXinUserInfoDTO.setEnable(jsonObject.getString("enables"));//成员启用状态。1表示启用的成员，0表示被禁用。注意，服务商调用接口不会返回此字段
            return weiXinUserInfoDTO;
        }
        return null;
    }

    /**
     * 获取用户ID
     *
     * @param code
     */
    public  String getUserID(String code) throws IOException {
        log.info("code：" + code);
        //逻辑判断token是否过期,如果过期刷新,没有过期直接拿来使用
        Object qwChatToken = stringRedisTemplate.opsForValue().get("qwChatToken");
        String token =qwChatToken !=null ? qwChatToken.toString() : this.getToken();
        return this.getUserIDByToken(token, code);
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public  WeiXinUserInfoDTO getUserInfo(String userId) throws IOException {
        //逻辑判断token是否过期,如果过期刷新,没有过期直接拿来使用
        String token = this.getToken();
        //根据userID和token获取用户的基本信息
        WeiXinUserInfoDTO weiXinUserInfoDTO = this.getUserInfoByID(token, userId);
        return weiXinUserInfoDTO;
    }
}



