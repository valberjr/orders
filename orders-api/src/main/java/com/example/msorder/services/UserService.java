package com.example.msorder.services;

import com.example.msorder.models.User;
import com.example.msorder.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.UUID;

@Named
@Transactional
public class UserService {

    @Inject
    private UserRepository repository;

    public User findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User save(User user) {
        return this.repository.save(user);
    }
}
