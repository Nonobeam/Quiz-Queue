package com.example.flashcard.controller;

import com.example.flashcard.controller.utils.BoxManager;
import com.example.flashcard.core.Card;
import com.example.flashcard.core.CardDTO;
import com.example.flashcard.service.CardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private static final Logger log = LoggerFactory.getLogger(CardController.class);
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

    @GetMapping("/study")
    public ResponseEntity<Set<Card>> study(@RequestParam String category) {
        try {
            Set<Card> cardSet = cardService.getCardsByCategory(category);
            log.info("Get all the cards by category {}", category);
            return ResponseEntity.ok(cardSet);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new HashSet<>());
        }
    }

    @GetMapping("/study/random")
    public ResponseEntity<Set<Card>> studyRandom(@RequestParam String category) {
        try {
            BoxManager boxManager = new BoxManager(cardService);
            Set<Card> cardSet = boxManager.getRandomCards(category);
            log.info("Get all random cards {}", category);
            return ResponseEntity.ok(cardSet);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new HashSet<>());
        }
    }

    @GetMapping("/study/all")
    public ResponseEntity<List<Card>> studyAll() {
        try {
            List<Card> cardSet = cardService.getAllCards();
            return ResponseEntity.ok(cardSet);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(new ArrayList<>());
        }
    }
}
