package com.example.flashcard.notification;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitmqReceiver {

    @Bean
    public Queue messageQueue() {
        return new Queue("message", false); // Create the queue
    }

    @RabbitListener(queues = "message")
    public void receive(String in) throws InterruptedException {
        System.out.println("Messsage: " + in);
    }
}