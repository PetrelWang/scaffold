package com.bbc.dto;

import lombok.Data;

/**
 * @Title: WeiXinLoginDTO
 * @Author WangHaoWei
 * @Package com.bbc.dto
 * @Date 2024/2/6 17:30
 * @description:
 */
@Data
public class WeiXinLoginDTO {
    private String appid;
    private String agentid;
    private String redirectUri;
}
