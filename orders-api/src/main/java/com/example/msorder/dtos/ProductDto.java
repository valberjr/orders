package com.example.msorder.dtos;

import com.example.msorder.models.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductDto(
        String id,
        @NotBlank
        @Length(min = 3, max = 100)
        String name,
        @DecimalMin(value = "0.00")
        BigDecimal price
) {
    public static ProductDto toEntity(Product product) {
        return new ProductDto(
                product.getId().toString(),
                product.getName(),
                product.getPrice()
        );
    }
}
