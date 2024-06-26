package org.example;

//import org.example.services.CircuitBreaker;
import org.example.services.NewCircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class Main implements CommandLineRunner {
//    @Autowired
//    CircuitBreaker circuitBreaker;
    @Autowired
    NewCircuitBreaker circuitBreaker2;
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        stringRedisTemplate.opsForValue().set("STATE", "CLOSE");
//        for(int i=0; i<10000; i++){
//            Thread.sleep(100);
//            try{
//                int request = circuitBreaker.handleRequest("http://localhost:8082/calculator/div?x=1&y=0").get();
//                System.out.println(request);
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//        }
//    }

    @Override
    public void run(String... args) throws Exception {
       for(int i=0; i<10000; i++){
           Thread.sleep(100);
           try{
               int request = circuitBreaker2.handleRequest("http://localhost:8082/calculator/div?x=1&y=1").get();
               System.out.println(request);
           }catch (RuntimeException e){
               System.out.println(e.getMessage());
           }
       }
    }
}
