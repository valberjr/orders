package com.example.msorder.dtos;

import com.example.msorder.models.User;

import java.util.List;

public record UserResponse(String id, String username, String name, List<String> roles) {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getUsername(),
                user.getName(),
                user.getRoles());
    }
}
