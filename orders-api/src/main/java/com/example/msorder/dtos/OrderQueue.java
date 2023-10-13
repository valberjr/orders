package com.example.msorder.dtos;

import com.example.msorder.models.Order;

import java.util.List;

public record OrderQueue(String id, String status, UserQueue user, List<OrderItemQueue> items) {
    public static OrderQueue toQueue(Order order) {
        return new OrderQueue(
                order.getId().toString(),
                order.getStatus().name(),
                UserQueue.toQueue(order.getUser()),
                order.getItems().stream().map(OrderItemQueue::toQueue).toList()
        );
    }
}
