package com.example.msorder.dtos;

import com.example.msorder.models.User;

public record UserQueue(String id, String name) {
    public static UserQueue toQueue(User user) {
        return new UserQueue(user.getId().toString(), user.getName());
    }
}
