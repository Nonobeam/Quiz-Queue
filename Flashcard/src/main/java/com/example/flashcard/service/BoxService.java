package com.example.flashcard.service;

import com.example.flashcard.core.Box;
import com.example.flashcard.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;

    public void save(Box box) {
        boxRepository.save(box);
    }

    public Box getBoxByTittleAndAuthor(String tittle, String author) {
        return boxRepository.getBoxByTitleAndAuthor(tittle, author);
    }
}
