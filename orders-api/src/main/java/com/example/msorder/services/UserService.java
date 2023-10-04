package com.example.msorder.services;

import com.example.msorder.models.User;
import com.example.msorder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional
    public User findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
