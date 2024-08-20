package com.example.account.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyBean {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "test")
    public void processMessage(String content) {
        System.out.println(content);
    }
}
