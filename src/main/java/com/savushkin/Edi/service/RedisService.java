package com.savushkin.Edi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void save(String key, String value) {
//        redisTemplate.opsForValue().set(key, value, 24, TimeUnit.HOURS);
        stringRedisTemplate.opsForValue().set(key, value, 24, TimeUnit.HOURS);
    }

//    @Cacheable(value = "dsdsfsv", key = "#key")
    public String findById(String key) {
//        return redisTemplate.opsForValue().get(key);
        return stringRedisTemplate.opsForValue().get(key);
    }
}
