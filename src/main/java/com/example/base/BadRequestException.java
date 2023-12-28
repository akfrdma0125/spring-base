package com.example.base;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("Bad Request");
    }
}
