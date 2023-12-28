package com.example.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BaseApplication.class, args);
        String[] parts = new CalculationRequestReader().read();
        long num1 = Long.parseLong(parts[0]);
        long num2 = Long.parseLong(parts[2]);
        String operator = parts[1];
        Calculator calculator = new Calculator();
        System.out.println(calculator.calculate(num1, num2, operator));
    }

}
