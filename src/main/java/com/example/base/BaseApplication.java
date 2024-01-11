package com.example.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        CalculationRequest calculationRequest = new CalculationRequestReader().read();
        System.out.println(new Calculator().calculate
                (calculationRequest.getNum1(), calculationRequest.getNum2(), calculationRequest.getOperator()));
    }

}
