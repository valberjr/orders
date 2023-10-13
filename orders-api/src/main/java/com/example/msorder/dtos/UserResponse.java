package com.example.msorder.dtos;

import com.example.msorder.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(String id, String username, String name, List<String> roles) {

    public UserResponse(String name) {
        this(null, null, name, null);
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getUsername(),
                user.getName(),
                user.getRoles());
    }

    public static UserResponse toResponseUserOnly(User user) {
        return new UserResponse(user.getName());
    }
}
