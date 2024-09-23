package com.example.notification.service;

import com.example.notification.component.Send;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final Send send;

    public String sendRabbitmqMessage(){
        send.send();
        return "Message sent to the RabbitMQ Successfully";
    }
}
