package com.savushkin.Edi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    private final RedisService redisService;

    @Autowired
    public TestService(RedisService redisService) {
        this.redisService = redisService;
    }


    public void test(){
        redisService.save("websrv1","Тестовое значение");
        System.out.println(redisService.findById("websrv1"));
    }

}
