package com.example.msorder.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderItemRequest(
        @Min(value = 1)
        Integer quantity,
        @NotEmpty
        List<ProductRequest> products
) {
}
