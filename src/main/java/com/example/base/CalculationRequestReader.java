package com.example.base;

import java.util.Scanner;

public class CalculationRequestReader {

        public CalculationRequest read() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter two numbers and an operator like this: 1 + 2");
            String result = scanner.nextLine();
            return new CalculationRequest(result.split(" "));
        }
}
