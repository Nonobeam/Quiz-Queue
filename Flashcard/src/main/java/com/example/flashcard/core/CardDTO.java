package com.example.flashcard.core;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private String question;
    private String answer;
    private String questionType;
    private String answerType;
    private String category;

    public Card convertToCard(){
        return Card.builder()
                .question(this.question)
                .answer(this.answer)
                .questionType(this.questionType)
                .answerType(this.answerType)
                .category(this.category)
                .build();
    }
}
