package com.example.flashcard.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("Card")
public class Card {
    @Id
    private String id;
    private String question;
    private String answer;
    private String questionType;
    private String answerType;
    private String category;
}
