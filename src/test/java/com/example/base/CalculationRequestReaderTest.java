package com.example.base;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculationRequestReaderTest {
    @Test
    public void test_read() {
        //given
        CalculationRequestReader calculationRequestReader = new CalculationRequestReader();

        //when
        System.setIn(new ByteArrayInputStream("1 + 2".getBytes()));
        CalculationRequest result = calculationRequestReader.read();

        //then
        assertThat(result).isEqualTo(new CalculationRequest(new String[]{"1", "+", "2"}));
    }
}
