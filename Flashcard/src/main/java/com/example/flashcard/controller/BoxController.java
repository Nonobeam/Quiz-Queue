package com.example.flashcard.controller;

import com.example.flashcard.core.BoxDTO;
import com.example.flashcard.service.BoxService;
import com.example.flashcard.service.CardService;
import com.example.flashcard.service.KafkaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RequestMapping("/box")
@RestController
@AllArgsConstructor
public class BoxController {
    private static final Logger log = LoggerFactory.getLogger(BoxController.class);
    private final BoxService boxService;
    private final KafkaService kafkaService;

    @PostMapping("/create")
    public void createBox(@RequestBody BoxDTO boxDTO) {
        kafkaService.sendMessage("box", "box", "author");
        String content = "";
        String author = kafkaService.processMessage(content);
        boxDTO.setAuthor(author);
        boxService.save(boxDTO.deserialize());
    }
}
