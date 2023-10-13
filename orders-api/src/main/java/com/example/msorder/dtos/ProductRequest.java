package com.example.msorder.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequest(
        String id,
        @NotBlank
        @Length(min = 3, max = 100)
        String name,
        @DecimalMin(value = "0.00")
        BigDecimal price
) {
}
