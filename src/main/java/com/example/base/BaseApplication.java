package com.example.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BaseApplication.class, args);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two numbers and an operator like this: 1 + 2");
        String result = scanner.nextLine();
        String[] parts = result.split(" ");
        long num1 = Long.parseLong(parts[0]);
        long num2 = Long.parseLong(parts[2]);
        String operator = parts[1];
        Calculator calculator = new Calculator();
        System.out.println(calculator.calculate(num1, num2, operator));
    }

}
