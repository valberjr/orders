package com.example.msorder.dto;

import com.example.msorder.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(String id, String username, String name, List<String> roles)
        implements Serializable {

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
