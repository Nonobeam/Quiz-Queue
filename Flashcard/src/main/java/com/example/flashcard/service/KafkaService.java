package com.example.flashcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "box")
    public String processMessage(String content) {
        return content;
    }

    public void sendMessage(String topic, String key, String content) {
        kafkaTemplate.send(topic, key, content);
    }
}
