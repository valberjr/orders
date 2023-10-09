package com.example.msorder.dtos;

import com.example.msorder.models.User;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public record UserDto(
        String id,
        @NotBlank
        @Length(min = 3, max = 100)
        String name) implements Serializable {

    public static UserDto toEntity(User user) {
        return new UserDto(user.getId().toString(), user.getName());
    }
}
