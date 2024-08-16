package com.example.chatting.controller;

import com.example.chatting.core.Message;
import com.example.chatting.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @MessageMapping("/send")
    @SendTo("/topic/public")
    public Message send(@Payload Message message) {
        return message;
    }
}
