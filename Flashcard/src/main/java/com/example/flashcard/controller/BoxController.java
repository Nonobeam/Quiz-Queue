package com.example.flashcard.controller;

import com.example.flashcard.controller.utils.BoxManager;
import com.example.flashcard.core.Card;
import com.example.flashcard.service.CardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RequestMapping("/box")
@RestController
@AllArgsConstructor
public class BoxController {
    private static final Logger log = LoggerFactory.getLogger(BoxController.class);
    private final CardService cardService;

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
}
