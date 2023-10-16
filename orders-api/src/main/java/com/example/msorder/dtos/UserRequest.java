package com.example.msorder.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRequest(String id, String username, String name, List<String> roles) {
    public UserRequest(String id, String name) {
        this(id, name, null, null);
    }

}
