package com.bbc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: JdbcParamConfig
 * @Author WangHaoWei
 * @Package com.bbc.config
 * @Date 2024/2/6 10:53
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.click")
public class JdbcParamConfig {
    private String driverClassName ;
    private String url ;
    private Integer initialSize ;
    private Integer maxActive ;
    private Integer minIdle ;
    private Integer maxWait ;
    private String username;
    private String password;
    // 省略 GET 和 SET
}
