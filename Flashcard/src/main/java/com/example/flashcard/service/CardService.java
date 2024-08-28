package com.example.flashcard.service;

import com.example.flashcard.core.Card;
import com.example.flashcard.core.CardDTO;
import com.example.flashcard.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepo;

    public Set<Card> getCardsByCategory(String category) {
        return cardRepo.getCardsByCategory(category);
    }

    public Card addCard(CardDTO cardDTO) {
        try {
            Card card = cardDTO.convertToCard();
            cardRepo.save(card);
            return card;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Card> getAllCards() {
        return cardRepo.findAll();
    }
}
