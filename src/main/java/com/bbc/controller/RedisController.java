package com.bbc.controller;

import com.bbc.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Title: RedisController
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2024/1/9 15:11
 * @description: redis的测试类
 */
@RestController
@RequestMapping(value = "redis")
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("setValue")
    public ResponseResult opsForValue(){
        stringRedisTemplate.opsForValue().set("name", "hello ",100, TimeUnit.SECONDS);;

        return ResponseResult.success();
    }

}
