package com.example.base;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculationRequestTest {
    @Test
    public void test_for_parsing_valid_number() {
        //given
        String[] parts = new String[]{"1", "+", "2"};

        //when
        CalculationRequest result = new CalculationRequest(parts);

        //then
        assertThat(result).isEqualTo(new CalculationRequest(new String[]{"1", "+", "2"}));
    }

    @Test
    public void test_for_parsing_valid_number_over_digits() {
        //given
        String[] parts = new String[]{"1000", "+", "212"};

        //when
        CalculationRequest result = new CalculationRequest(parts);

        //then
        assertThat(result).isEqualTo(new CalculationRequest(new String[]{"1000", "+", "212"}));
    }

    @Test
    public void test_for_parsing_invalid_parts_length() {
        //given
        String[] parts = new String[]{"1000", "+"};

        //when
        assertThrows(BadRequestException.class, () -> new CalculationRequest(parts));
    }

    @Test
    public void test_for_parsing_invalid_operator() {
        //given
        String[] parts = new String[]{"1000", "@", "1234"};

        //when
        assertThrows(BadRequestException.class, () -> new CalculationRequest(parts));
    }

    @Test
    public void test_for_parsing_invalid_operator_length() {
        //given
        String[] parts = new String[]{"1000", "+-", "1234"};

        //when
        assertThrows(BadRequestException.class, () -> new CalculationRequest(parts));
    }
}
