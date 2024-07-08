package com.bbc.entity;

import lombok.Data;

/**
 * @Title: Production
 * @Author WangHaoWei
 * @Package com.bbc.entity
 * @Date 2024/2/23 20:13
 * @description:
 */
@Data
public class Production {
    private String oilFieldName;
    private String wellName;
    private String date;
    private String gas;
    private String water;
    private String oilPressure;
    private String casingPressure;
    private String temperature;
}
