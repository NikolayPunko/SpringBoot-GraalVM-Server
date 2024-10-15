package com.savushkin.Edi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaListenerService {


    @KafkaListener(topics = "websrv", groupId = "consumer1")
    void listener1(String data) {
        log.info("Прослушано сообщение из топика websrv  - {}", data);

    }

}
