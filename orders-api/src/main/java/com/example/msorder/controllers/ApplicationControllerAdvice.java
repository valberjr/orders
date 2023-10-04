package com.example.msorder.controllers;

import com.example.msorder.dtos.ExceptionResponseDto;
import com.example.msorder.exceptions.InvalidJwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleIllegalArgumentException(IllegalArgumentException e) {
        return new ExceptionResponseDto(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleBadCredentialsException(Exception e) {
        return new ExceptionResponseDto(e.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleDisabledException(Exception e) {
        return new ExceptionResponseDto(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleJwtException(Exception e) {
        return new ExceptionResponseDto(e.getMessage());
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionResponseDto handleInvalidJwtAuthenticationException(Exception e) {
        return new ExceptionResponseDto(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseDto handleException(Exception e) {
        return new ExceptionResponseDto(e.getMessage());
    }
}
