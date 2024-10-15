package com.savushkin.Edi.service;

import com.savushkin.Edi.model.RequestObj;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaListenerService {

    private final ScriptService scriptService;
    private final RedisService redisService;

    @Autowired
    public KafkaListenerService(ScriptService scriptService, RedisService redisService) {
        this.scriptService = scriptService;
        this.redisService = redisService;
    }

    @KafkaListener(topics = "websrv", groupId = "consumer1")
    void listenerWebSrv1(String data) {
        log.info("Consumer1 начал обработку сообщения из топика websrv  - {}", data);

        JSONObject json = new JSONObject(data);
        String method = json.getString("method");
        String token = json.getString("token");
        String body = json.getString("body");
        String gln = json.getString("gln");

        String result = scriptService.executeClass(method, new RequestObj(body,gln));

        redisService.save(token, result);

        log.info("Consumer1 завершил обработку сообщения c id - {}", token);
    }

}
