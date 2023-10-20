package com.example.msorder.dto;

import com.example.msorder.model.User;

public record UserQueue(String id, String name) {
    public static UserQueue toQueue(User user) {
        return new UserQueue(user.getId().toString(), user.getName());
    }
}
