package com.example.msorder.config;

import com.example.msorder.service.CustomUserDetailsService;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomAuthenticationManager {

    @Inject
    private CustomUserDetailsService userDetailsService;
    @Inject
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager getCustomAuthenticationManager() {
        return authentication -> {
            var username = String.valueOf(authentication.getPrincipal());
            var password = String.valueOf(authentication.getCredentials());
            var user = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        };
    }
}
