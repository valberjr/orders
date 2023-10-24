package com.example.msorder.service;

import com.example.msorder.model.User;
import com.example.msorder.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Named
public class CustomUserDetailsService implements UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
