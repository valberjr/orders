package com.example.msorder.dtos;

import com.example.msorder.models.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderItemDto(
        String id,
        @Min(value = 1)
        Integer quantity,
        @NotEmpty
        List<ProductDto> products
) {
    public static OrderItemDto toEntity(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId().toString(),
                orderItem.getQuantity(),
                orderItem.getProducts().stream().map(ProductDto::toEntity).toList()
        );
    }
}
