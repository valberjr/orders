package com.example.msorder.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID id,
        @NotBlank
        String status,
        @NotNull
        UserDto user,
        @NotEmpty
        List<OrderItemDto> items) {
}
