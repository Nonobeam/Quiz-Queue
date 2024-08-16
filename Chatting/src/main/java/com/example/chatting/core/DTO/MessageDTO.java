package com.example.chatting.core.DTO;

import com.example.chatting.core.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO {
    private String sender;
    private String receiver;
    private String text;

    public Message convertToMessage() {
        return Message.builder()
                .sender(this.sender)
                .receiver(this.receiver)
                .text(this.text)
                .build();
    }
}
