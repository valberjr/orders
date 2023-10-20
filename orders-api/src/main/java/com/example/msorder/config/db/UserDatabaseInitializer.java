package com.example.msorder.config.db;

import com.example.msorder.model.User;
import com.example.msorder.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Named
@Order(1)
@Slf4j
public class UserDatabaseInitializer implements CommandLineRunner {

    @Inject
    private UserRepository userRepository;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        this.userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .name("jessica")
                .roles(List.of("ROLE_USER"))
                .build());
        this.userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .name("victoria")
                .roles(List.of("ROLE_ADMIN"))
                .build());
        log.debug("Initialized database with test users.");
        this.userRepository
                .findAll()
                .forEach(user -> log.debug(String.format("User: %s", user.getUsername())));
    }
}
