package com.example.flashcard.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("Box")
public class Box {
    @Id
    private String id;
    private String title;
    private String description;
    private String author;
    private String category;
    private Set<Card> cards;
}
