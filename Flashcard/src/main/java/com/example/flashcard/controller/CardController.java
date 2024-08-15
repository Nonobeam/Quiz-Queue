package com.example.flashcard.controller;

import com.example.flashcard.core.Card;
import com.example.flashcard.core.DTO.CardDTO;
import com.example.flashcard.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/add")
    public ResponseEntity<Card> addCard(@RequestBody CardDTO card) {
        Card newCard = cardService.addCard(card);

        if (newCard != null) {
            return ResponseEntity.ok(newCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
