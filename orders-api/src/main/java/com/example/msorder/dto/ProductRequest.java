package com.example.msorder.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be blank")
        @Length(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        String name,
        @DecimalMin(value = "0.00", message = "Product price cannot be less than 0.00")
        BigDecimal price
) {
}
