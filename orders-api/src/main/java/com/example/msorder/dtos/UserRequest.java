package com.example.msorder.dtos;

import com.example.msorder.models.User;

import java.util.List;

public record UserRequest(String id, String username, String name, List<String> roles) {
    public UserRequest(String id, String name) {
        this(id, name, null, null);
    }

    public static UserRequest toResponse(User user) {
        return new UserRequest(
                user.getId().toString(),
                user.getUsername(),
                user.getName(),
                user.getRoles());
    }
}
