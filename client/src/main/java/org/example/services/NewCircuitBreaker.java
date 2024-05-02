package org.example.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@RequiredArgsConstructor
public class NewCircuitBreaker {
    private final RestTemplate restTemplate;
    private LocalDateTime failureTime;
    private Long intervalSeconds = 1L;
    private boolean isOpen = false;
    private int failuresCount;

    public void open() {
        isOpen = true;
        failureTime = LocalDateTime.now();
    }

    public void close() {
        isOpen = false;
        failuresCount = 0;
    }

    public CompletableFuture<Integer> handleRequest(String url) {
        if(isOpen){
            if(failureTime==null || failureTime.until(LocalDateTime.now(), SECONDS)>=intervalSeconds) close();
            else throw new RuntimeException("Circuit breaker is opened");
        }

        CompletableFuture<Integer> res = new CompletableFuture<>();
        try {
            Integer obj = restTemplate.getForObject(url, Integer.class);
            res.complete(obj);
            return res;
        } catch (HttpServerErrorException e) {
            failuresCount++;
            if (failuresCount < 2) {
                return handleRequest(url); // Retry
            } else {
                open();
                throw new RuntimeException("Circuit breaker is opened");
            }
        }
    }
}