package com.example.search.repository;

import com.example.search.core.Flashcard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends ElasticsearchRepository<Flashcard, Long> {
    Optional<Flashcard> findByAnswer(String answer);
    Optional<Flashcard> findByQuestionAndAnswer(String question, String answer);

    List<Flashcard> findByQuestion(String type);
}
