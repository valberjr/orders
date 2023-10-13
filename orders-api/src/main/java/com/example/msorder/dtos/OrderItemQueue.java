package com.example.msorder.dtos;

import com.example.msorder.models.OrderItem;

import java.util.List;

public record OrderItemQueue(Integer quantity, List<ProductQueue> products) {
    public static OrderItemQueue toQueue(OrderItem orderItem) {
        return new OrderItemQueue(
                orderItem.getQuantity(),
                orderItem.getProducts().stream().map(ProductQueue::toQueue).toList()
        );
    }
}
