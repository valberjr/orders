package com.example.msorder.dtos;

import com.example.msorder.models.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

public record OrderItemDto(
        String id,
        @Min(value = 1)
        Integer quantity,
        @NotEmpty
        List<ProductDto> products
) implements Serializable {
    public static OrderItemDto toEntity(OrderItem orderItem) {
        var id = orderItem.getId() != null ? String.valueOf(orderItem.getId()) : null;
        return new OrderItemDto(
                id,
                orderItem.getQuantity(),
                orderItem.getProducts().stream().map(ProductDto::toEntity).toList()
        );
    }
}
