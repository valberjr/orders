package com.example.msorder.configs;

import com.example.msorder.models.User;
import com.example.msorder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class UserDatabaseInitializer implements
        CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
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
