package com.example.msorder.dtos;

import com.example.msorder.models.Order;

import java.util.List;

public record OrderResponse(String id, String status, UserResponse user, List<OrderItemResponse> items) {
    public static OrderResponse toResponse(Order order) {
        var items = order.getItems().stream().map(OrderItemResponse::toResponse).toList();
        return new OrderResponse(
                String.valueOf(order.getId()),
                String.valueOf(order.getStatus()),
                UserResponse.toResponseUserOnly(order.getUser()),
                items
        );
    }
}
