package com.example.msorder.dtos;

import com.example.msorder.models.OrderItem;

import java.util.List;

public record OrderItemResponse(Integer quantity, List<ProductResponse> products) {
    public static OrderItemResponse toResponse(OrderItem orderItem) {
        Integer qty = orderItem.getQuantity();
        var productsList = orderItem.getProducts().stream().map(ProductResponse::toResponse).toList();
        return new OrderItemResponse(qty, productsList);
    }
}
