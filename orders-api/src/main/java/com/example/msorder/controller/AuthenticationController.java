package com.example.msorder.controller;

import com.example.msorder.config.jwt.JwtTokenProvider;
import com.example.msorder.dto.AuthenticationResponse;
import com.example.msorder.model.UserAuthentication;
import com.example.msorder.model.User;
import jakarta.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Inject
    private AuthenticationManager authenticationManager;
    @Inject
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse signin(@RequestBody UserAuthentication request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        var token = jwtTokenProvider.createToken(authentication);

        var user = (User) authentication.getPrincipal();
        var userId = String.valueOf(user.getId());
        var name = user.getName();

        return new AuthenticationResponse(token, userId, name);
    }

}
