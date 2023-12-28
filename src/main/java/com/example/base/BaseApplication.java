package com.example.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BaseApplication.class, args);
        CalculationRequest calculationRequest = new CalculationRequestReader().read();
        System.out.println(new Calculator().calculate
                (calculationRequest.getNum1(), calculationRequest.getNum2(), calculationRequest.getOperator()));
    }

}
