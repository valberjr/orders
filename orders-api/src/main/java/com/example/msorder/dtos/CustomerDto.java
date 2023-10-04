package com.example.msorder.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        @NotBlank
        @Length(min = 3, max = 100)
        String name) {
}
