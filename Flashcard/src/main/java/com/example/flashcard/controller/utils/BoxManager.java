package com.example.flashcard.controller.utils;

import com.example.flashcard.core.Card;
import com.example.flashcard.service.CardService;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class BoxManager {
    private static Set<Card> cardsList;
    private final CardService cardService;

    public Set<Card> getCards(String category) {
        return cardService.getCardsByCategory(category);
    }

    public Set<Card> getRandomCards(String category) {
        Set<Card> randomCards = new HashSet<>(){
            @Nonnull
            @Override
            public Iterator<Card> iterator()
            {
                return new RandomizingIterator<>(super.iterator());
            }

            class RandomizingIterator<T> implements Iterator<T> {
                final Iterator<T> iterator;

                private RandomizingIterator(@Nonnull final Iterator<T> iterator) {
                    List<T> list = new ArrayList<>();
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                    Collections.shuffle(list);
                    this.iterator = list.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.iterator.hasNext();
                }

                @Override
                public T next() {
                    return this.iterator.next();
                }
            }
        };

        randomCards.addAll(getCards(category));

        return randomCards;
    }
}
