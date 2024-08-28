package com.example.account.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "box")
    public void processMessage(String content) {
        log.info(content);
    }

    public void sendMessage(String topic, String key, String content) {
        kafkaTemplate.send(topic, key, content);
    }
}
