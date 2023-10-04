package com.example.msorder.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String message, Object... args) {
        super(String.format(message, args));
    }
}