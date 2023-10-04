package com.example.msorder.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {

    private String secretKey = "R9kV7E4oLj4xEMFJiqixz2Umjbviyp";
    private long validityInMs = 3600000; // 1 hour
}
