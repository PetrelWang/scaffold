package com.bbc.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Title: RedissonConf
 * @Author WangHaoWei
 * @Package com.bbc.config
 * @Date 2024/3/15 17:22
 * @description:
 */
@Configuration
public class RedissonConf {//加载redisson配置
    @Autowired
    private Environment env;
    @Bean
    public RedissonClient redissonClient(){
        Config cfg=new Config();
        cfg.useSingleServer().
                setAddress(env.getProperty("redisson.host.config")).
                setPassword("123456").
                setKeepAlive(true);

        return Redisson.create(cfg);
    }
}

