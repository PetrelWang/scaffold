package com.bbc.common;

/**
 * @Title: WecomUtil
 * @Author WangHaoWei
 * @Package com.bbc.common
 * @Date 2024/2/6 17:26
 * @description:
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信配置
 *
 * @author wangjunjie
 * @version 1.0
 * @date 2023/5/30 20:50
 */
@Configuration
public class WecomUtil {
    //企业ID
    @Value("${qwchat.appId}")
    public String appId;

    //应用AgentId
    @Value("${qwchat.agentId}")
    public  String agentId;

    //第三方网站指定自己的端口
    @Value("${qwchat.redirectUri}")
    public  String redirectUri;

    @Value("${qwchat.secret}")
    public  String secret;

    public  String accessTokenUrl;

    @Value("${qwchat.accessTokenUrl}")
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getUserIDUrl;

    @Value("${qwchat.getUserIDUrl}")
    public void setGetUserIDUrl(String getUserIDUrl) {
        this.getUserIDUrl = getUserIDUrl;
    }

    public  String getUserUrl;

    @Value("${qwchat.getUserUrl}")
    public void setGetUserUrl(String getUserUrl) {
        this.getUserUrl = getUserUrl;
    }

}

