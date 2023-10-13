package com.example.msorder.dtos;

import com.example.msorder.models.Product;

import java.math.BigDecimal;

public record ProductQueue(String name, BigDecimal price) {
    public static ProductQueue toQueue(Product product) {
        return new ProductQueue(product.getName(), product.getPrice());
    }
}
