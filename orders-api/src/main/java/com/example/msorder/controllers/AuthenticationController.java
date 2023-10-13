package com.example.msorder.controllers;

import com.example.msorder.configs.jwt.JwtTokenProvider;
import com.example.msorder.dtos.AuthenticationResponse;
import com.example.msorder.models.AuthenticationRequest;
import jakarta.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public AuthenticationResponse signin(@RequestBody AuthenticationRequest request) {
        var username = request.getUsername();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
        var token = jwtTokenProvider.createToken(authentication);
        return new AuthenticationResponse(username, token);
    }

}
