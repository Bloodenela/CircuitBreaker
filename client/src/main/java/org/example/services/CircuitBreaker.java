//package org.example.services;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//import static java.time.temporal.ChronoUnit.SECONDS;
//import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
//
//@Service
//@RequiredArgsConstructor
//public class CircuitBreaker {
//    private LocalDateTime timeFailure;
//    @Value("${circuitbreaker.interval}")
//    private Long intervalSeconds;
//    private final RestTemplate restTemplate;
//    private final StringRedisTemplate redisTemplate;
//
//    @PostConstruct
//    public void init(){
//        redisTemplate.opsForValue().setIfAbsent("STATE","CLOSED");
//    }
//
//    private String getState(){
//        return redisTemplate.opsForValue().get("STATE");
//    }
//
//    private void close(){
//        redisTemplate.opsForValue().set("STATE", "CLOSE");
//        timeFailure = null;
//    }
//
//    private void open(){
//        redisTemplate.opsForValue().set("STATE", "OPEN");
//        timeFailure = LocalDateTime.now();
//    }
//
//
//    public CompletableFuture<Integer> handleRequest(String url) throws ExecutionException, InterruptedException {
//        if (getState().equals("OPEN")) {
//            if (timeFailure.until(LocalDateTime.now(), SECONDS) >= intervalSeconds) {
//                close();
//            } else {
//                throw new RuntimeException("Circuit breaker opened");
//            }
//        }
//
//        CompletableFuture<Integer> res = new CompletableFuture<>();
//
//        try {
//            Integer forObj = restTemplate.getForObject(url, Integer.class);
//            res.complete(forObj);
//            return res;
//        } catch (Exception e) {
//            newSingleThreadScheduledExecutor().schedule(() -> {
//                try {
//                    Integer forObj = restTemplate.getForObject(url, Integer.class);
//                    res.complete(forObj);
//                } catch (Exception secondE) {
//                    open();
//                    throw new RuntimeException("Circuit breaker opened");
//                }
//                return res;
//            }, intervalSeconds, TimeUnit.SECONDS).get();
//        }
//        return res;
//    }
//}
