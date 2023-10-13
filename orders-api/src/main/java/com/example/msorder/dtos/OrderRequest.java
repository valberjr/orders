package com.example.msorder.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotBlank
        String status,
        @NotNull
        UserRequest user,
        @NotEmpty
        List<OrderItemRequest> items) {
}
