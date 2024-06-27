package com.evilcorp.api;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.Data;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookProvider {

    // HttpClient - java.net (позже разобрать)
    // RestTemplate - spring.web (позже разобрать)
    // WebClient - spring.reactive (позже разобрать)

    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient) {
        webClient = WebClient.builder().build();
        this.eurekaClient = eurekaClient;
    }

    public UUID getRandomBookId(){
        // Цель: Вызвать запрос GET http:\\localhost:8180/api/book/random, получить ID и вернуть.

        BookResponse randomBook = webClient.get()
                .uri(getBookServiceIP() + "/api/book/random")
                .retrieve()
                .bodyToMono(BookResponse.class)
                .block(); // команда для "блокирования" текущего метода в потоке до его выполнения

        return randomBook.getId();
    }

    private String getBookServiceIP(){
        Application application = eurekaClient.getApplication("BOOK-SERVICE");
        List<InstanceInfo> instances = application.getInstances();

        InstanceInfo randomInstance = instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
        //return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
        return  randomInstance.getHomePageUrl();
    }

    @Data
    private static class BookResponse {
        private UUID id;
    }
}
