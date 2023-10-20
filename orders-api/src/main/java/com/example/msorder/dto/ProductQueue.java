package com.example.msorder.dto;

import com.example.msorder.model.Product;

import java.math.BigDecimal;

public record ProductQueue(String name, BigDecimal price) {
    public static ProductQueue toQueue(Product product) {
        return new ProductQueue(product.getName(), product.getPrice());
    }
}
