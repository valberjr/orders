package com.example.msorder.controllers;

import com.example.msorder.configs.jwt.JwtTokenProvider;
import com.example.msorder.models.AuthenticationRequest;
import jakarta.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Inject
    private AuthenticationManager authenticationManager;
    @Inject
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
        var username = data.getUsername();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        var token = jwtTokenProvider.createToken(authentication);
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }

}
