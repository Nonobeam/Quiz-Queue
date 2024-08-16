package com.example.chatting.service;

import com.example.chatting.core.Message;
import com.example.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
}
