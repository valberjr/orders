package com.example.msorder.dtos;

public record ExceptionResponseDto(
        String timestamp,
        int status,
        String error,
        String message) {
}
