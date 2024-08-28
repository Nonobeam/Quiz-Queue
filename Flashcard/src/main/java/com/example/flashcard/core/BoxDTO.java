package com.example.flashcard.core;

import com.example.flashcard.repository.BoxRepository;
import com.example.flashcard.service.BoxService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BoxDTO {
    private String title;
    private String description;
    private String author;
    private String category;

    private static final Logger log = LoggerFactory.getLogger(BoxDTO.class);
    private final BoxService boxService;

    public BoxDTO serialize(Box box) {
        return BoxDTO.builder()
                .title(title)
                .description(description)
                .author(author)
                .category(category)
                .build();
    }

    public Box deserialize() {
        Box box = null;
        try {
            box = boxService.getBoxByTittleAndAuthor(this.title, this.author);
        } catch (Exception e) {
            log.error("Error while deserializing BoxDTO", e);
        }

        return box;
    }
}
