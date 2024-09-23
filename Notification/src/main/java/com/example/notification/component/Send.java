package com.example.notification.component;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Send {
    private final RabbitTemplate mRabbitTemplate;

    @Bean
    private Queue messageQueue() {
        return new Queue("message", false);
    }

    public void send() {
        String message = "Hello World!";
        mRabbitTemplate.convertAndSend(messageQueue().getName(), message);
        System.out.println(" [x] Sent in queue1 '" + message + "'");
    }

}
