package com.example.msorder.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull
        UserRequest user,
        @NotEmpty
        List<OrderItemRequest> items) {
}
