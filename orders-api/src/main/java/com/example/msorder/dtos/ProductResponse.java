package com.example.msorder.dtos;

import com.example.msorder.models.Product;

import java.math.BigDecimal;

public record ProductResponse(String name, BigDecimal price) {
    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getName(), product.getPrice());
    }
}
