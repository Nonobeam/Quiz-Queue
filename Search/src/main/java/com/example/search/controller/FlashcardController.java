package com.example.search.controller;

import com.example.search.core.Flashcard;
import com.example.search.repository.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardRepository flashcardRepository;

    @PostMapping("/save")
    public Flashcard save(@RequestBody Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    @GetMapping("/get")
    public List<Flashcard> getFlashcardByQuestion(@RequestParam String question) {
        return flashcardRepository.findByQuestion(question);
    }
}
