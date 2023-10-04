package com.example.msorder.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record OrderItemDto(
        UUID id,
        @Min(value = 1)
        Integer quantity,
        @NotEmpty
        List<ProductDto> products
) {
}
