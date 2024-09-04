package com.example.search.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Builder
@Document(indexName = "flashcard")
public class Flashcard {
    @Id
    private String id;
    private String question;
    private String answer;
    private String type;
}
