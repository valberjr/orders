package com.example.msorder.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Order user cannot be null")
        UserRequest user,

        @NotEmpty(message = "List of items cannot be empty")
        List<OrderItemRequest> items) {
}
