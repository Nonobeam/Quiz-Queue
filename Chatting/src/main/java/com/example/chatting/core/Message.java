package com.example.chatting.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("Message")
public class Message {
    @Id
    private String id;
    private String sender;
    private String receiver;
    private String text;
}
