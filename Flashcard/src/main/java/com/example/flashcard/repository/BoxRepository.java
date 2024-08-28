package com.example.flashcard.repository;

import com.example.flashcard.core.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoxRepository extends MongoRepository<Box, String> {
    Box findByTitle(String title);
    Box getBoxByTitleAndAuthor(String title, String author);
}
