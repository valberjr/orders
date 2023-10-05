package com.example.msorder.dtos;

import com.example.msorder.models.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDto(
        String id,
        @NotBlank
        String status,
        @NotNull
        UserDto user,
        @NotEmpty
        List<OrderItemDto> items) {

    public static OrderDto toEntity(Order order) {
        var id = order.getId() != null ? String.valueOf(order.getId()) : null;
        return new OrderDto(
                id,
                order.getStatus().name(),
                UserDto.toEntity(order.getUser()),
                order.getItems().stream().map(OrderItemDto::toEntity).toList()
        );
    }
}
