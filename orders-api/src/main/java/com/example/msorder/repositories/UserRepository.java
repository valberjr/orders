package com.example.msorder.repositories;

import com.example.msorder.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository
        extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
