package com.example.flashcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.flashcard", "com.example.authorization.config"})
public class FlashcardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashcardApplication.class, args);
	}

}
