package com.evilcorp.api;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class BookProvider {

    // HttpClient - java.net (позже разобрать)
    // RestTemplate - spring.web (позже разобрать)
    // WebClient - spring.reactive (позже разобрать)

    private final WebClient webClient;

    public BookProvider() {
        webClient = WebClient.builder()


                .build();
    }

    public UUID getRandomBookId(){
        // Цель: Вызвать запрос GET http:\\localhost:8180/api/book/random, получить ID и вернуть.

        BookResponse randomBook = webClient.get()
                .uri("http://localhost:8180/api/book/random")
                .retrieve()
                .bodyToMono(BookResponse.class)
                .block(); // команда для "блокирования" текущего метода в потоке до его выполнения

        return randomBook.getId();
    }

    @Data
    private static class BookResponse {
        private UUID id;
    }
}
