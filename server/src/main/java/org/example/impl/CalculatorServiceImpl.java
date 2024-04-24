package org.example.impl;

import org.example.services.inter.CalculatorService;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int sum(int x, int y) {
        return x+y;
    }

    @Override
    public int minus(int x, int y) {
        return x-y;
    }

    @Override
    public int mul(int x, int y) {
        return x*y;
    }

    @Override
    public int div(int x, int y) {
        return x/y;
    }
}
