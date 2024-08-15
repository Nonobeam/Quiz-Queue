package com.example.flashcard.repository;

import com.example.flashcard.core.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Set;

@EnableMongoRepositories
public interface CardRepository extends MongoRepository<Card, String>{
    Set<Card> getCardsByCategory(String category);
}
