package com.bbc.dto;

import lombok.Data;

/**
 * @Title: WeiXinUserInfoDTO
 * @Author WangHaoWei
 * @Package com.bbc.dto
 * @Date 2024/2/6 17:42
 * @description:
 */
@Data
public class WeiXinUserInfoDTO {
    private String userid;
    private String name;
    private String mobile;
    private String email;
    private String avatar;
    private String enable;
}
