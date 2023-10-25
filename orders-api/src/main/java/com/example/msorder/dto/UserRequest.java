package com.example.msorder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRequest(
        String id,

        @NotEmpty(message = "Username cannot be empty")
        @Length(min = 5, max = 10, message = "Username must be between 5 and 10 characters")
        String username,

        @NotEmpty(message = "Name cannot be empty")
        @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        List<String> roles) {
    public UserRequest(String id, String name) {
        this(id, name, null, null);
    }

}
