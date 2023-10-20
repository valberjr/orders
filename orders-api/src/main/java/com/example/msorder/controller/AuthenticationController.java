package com.example.msorder.controller;

import com.example.msorder.config.jwt.JwtTokenProvider;
import com.example.msorder.dto.AuthenticationResponse;
import com.example.msorder.model.AuthenticationRequest;
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
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        var token = jwtTokenProvider.createToken(authentication);
        return new AuthenticationResponse(token);
    }

}
