package com.example.base;


import java.util.Objects;

public class CalculationRequest {
    //final 선언으로 VO(값 객체)로 만들어줌
    private final long num1;
    private final long num2;
    private final String operator;

    //VO는 항상 값이 유효해야 하므로, 검증 로직 추가
    public CalculationRequest(String[] parts) {
        if(parts.length != 3) throw new BadRequestException();
        if (parts[1].length() != 1) throw new BadRequestException();
        if (!parts[1].matches("[+\\-*/]")) throw new BadRequestException();
        this.num1 = Long.parseLong(parts[0]);
        this.num2 = Long.parseLong(parts[2]);
        this.operator = parts[1];
    }

    public long getNum1() {
        return num1;
    }

    public long getNum2() {
        return num2;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalculationRequest that = (CalculationRequest) o;

        if (num1 != that.num1) return false;
        if (num2 != that.num2) return false;
        return Objects.equals(operator, that.operator);
    }

    @Override
    public int hashCode() {
        int result = (int) (num1 ^ (num1 >>> 32));
        result = 31 * result + (int) (num2 ^ (num2 >>> 32));
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }
}
