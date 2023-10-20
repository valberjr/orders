package com.example.msorder.dto;

import com.example.msorder.model.Order;

import java.io.Serializable;
import java.util.List;

public record OrderResponse(String id, String status, UserResponse user, List<OrderItemResponse> items)
        implements Serializable {
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
