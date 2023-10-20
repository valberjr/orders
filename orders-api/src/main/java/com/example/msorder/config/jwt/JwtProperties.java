package com.example.msorder.config.jwt;

import jakarta.inject.Named;

@Named
public class JwtProperties {

    public String getSecretKey() {
        return "R9kV7E4oLj4xEMFJiqixz2Umjbviyp";
    }

    public long getValidityInMs() {
        return 60L * 60 * 1000; // 1 hour
    }
}
