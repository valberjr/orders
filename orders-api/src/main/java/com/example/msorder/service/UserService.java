package com.example.msorder.service;

import com.example.msorder.dto.UserResponse;
import com.example.msorder.model.User;
import com.example.msorder.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Named
@Transactional
public class UserService {

    @Inject
    private UserRepository repository;

    public List<UserResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(UserResponse::toResponse)
                .toList();
    }

    public User findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User save(User user) {
        return this.repository.save(user);
    }

}
