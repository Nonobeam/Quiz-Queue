package com.example.search.service;

import com.example.search.core.Flashcard;
import com.example.search.repository.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;

    public void saveFlashcard(Flashcard flashcard) {
        flashcardRepository.save(flashcard);
    }

    public List<Flashcard> getFlashcardsByQuestion(String question) {
        return flashcardRepository.findByQuestion(question);
    }

//    public List<Flashcard> searchFlashcardByQuestion(String searchTerm) {
//        MatchQueryBuilder query = QueryBuilders.matchQuery("content", searchTerm);
//        Iterable<MyDocument> result = documentRepository.search(query);
//        return StreamSupport.stream(result.spliterator(), false)
//                .collect(Collectors.toList());
//    }
}
