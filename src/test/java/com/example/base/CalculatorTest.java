package com.example.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @Test
    public void test_for_plus() {
        //given
        long num1 = 2;
        long num2 = 3;
        String operator = "+";
        Calculator calculator = new Calculator();

        //when
        long result = calculator.calculate(num1, num2, operator);

        //then
        assertEquals(5, result); // JUnit assertion
        assertThat(result).isEqualTo(5); // AssertJ assertion
    }

    @Test
    public void test_for_minus() {
        //given
        long num1 = 2;
        long num2 = 3;
        String operator = "-";
        Calculator calculator = new Calculator();

        //when
        long result = calculator.calculate(num1, num2, operator);

        //then
        assertThat(result).isEqualTo(-1); // AssertJ assertion
    }

    @Test
    public void test_for_multiplication() {
        //given
        long num1 = 2;
        long num2 = 3;
        String operator = "*";
        Calculator calculator = new Calculator();


        //when
        long result = calculator.calculate(num1, num2, operator);

        //then
        assertThat(result).isEqualTo(6); // AssertJ assertion
    }

    @Test
    public void test_for_division() {
        //given
        long num1 = 2;
        long num2 = 3;
        String operator = "/";
        Calculator calculator = new Calculator();

        //when
        long result = calculator.calculate(num1, num2, operator);

        //then
        assertThat(result).isEqualTo(0); // AssertJ assertion
    }

    @Test
    public void test_for_invalid_operator() {
        //given
        long num1 = 2;
        long num2 = 3;
        String operator = "@";
        Calculator calculator = new Calculator();

        //when
        //then
        //람다식을 돌렸을 때, InvalidOperatorException이 발생하는지 확인
        assertThrows(InvalidOperatorException.class, () -> calculator.calculate(num1, num2, operator));
    }
}
