package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.inter.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping("/sum")
    ResponseEntity<Integer> sum(@RequestParam int x, @RequestParam int y){
        return new ResponseEntity<>(calculatorService.sum(x,y), HttpStatus.OK);
    }
    @GetMapping("/min")
    ResponseEntity<Integer> min(@RequestParam int x, @RequestParam int y){
        return new ResponseEntity<>(calculatorService.minus(x,y), HttpStatus.OK);
    }
    @GetMapping("/mul")
    ResponseEntity<Integer> mul(@RequestParam int x, @RequestParam int y){
        return new ResponseEntity<>(calculatorService.mul(x,y), HttpStatus.OK);
    }
    @GetMapping("/div")
    ResponseEntity<Integer> div(@RequestParam int x, @RequestParam int y){
        return new ResponseEntity<>(calculatorService.div(x,y), HttpStatus.OK);
    }
}
