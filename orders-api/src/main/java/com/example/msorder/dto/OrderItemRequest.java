package com.example.msorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderItemRequest(
        @Min(value = 1, message = "Order item quantity cannot be less than 1")
        Integer quantity,

        @NotEmpty(message = "List of products cannot be empty")
        List<ProductRequest> products
) {
}
