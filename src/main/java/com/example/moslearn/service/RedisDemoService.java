package com.example.moslearn.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisDemoService {
    private final StringRedisTemplate redisTemplate;
    public RedisDemoService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void demo() {
        // 写入数据
        redisTemplate.opsForValue().set("mykey", "Hello Redis!");
        // 读取数据
        String value = redisTemplate.opsForValue().get("mykey");
        System.out.println("Value from Redis: " + value);
    }

    public String demo2() {
        redisTemplate.opsForValue().set("mykey","zzs1231456");
        System.out.println("Value from Redis: " + redisTemplate.opsForValue().get("mykey"));
        return redisTemplate.opsForValue().get("mykey");
    }
}
