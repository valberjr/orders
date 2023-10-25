package com.example.msorder.exception;

import com.example.msorder.util.DateUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Spring Security Exceptions
     */

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        log.error("An AccessDeniedException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleAuthenticationException(BadCredentialsException e) {
        log.error("An BadCredentialsException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException e) {
        log.error("An ExpiredJwtException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException e) {
        log.error("An SignatureException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleSignatureException(UsernameNotFoundException e) {
        log.error("An UsernameNotFoundException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    /**
     * Controllers Exceptions
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        log.error("An ConstraintViolationException has occurred", e);
        var messageTemplate = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst()
                .orElse("");
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, messageTemplate);
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        log.error("An Exception has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("An IllegalArgumentException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ProblemDetail handleTransactionSystemException(TransactionSystemException e) {
        if (e.getRootCause() instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolationException(constraintViolationException);
        }
        log.error("An TransactionSystemException has occurred", e);
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getLocalizedMessage());
        problemDetail.setProperty("timestamp", DateUtil.toISOInstant(Instant.now()));
        return problemDetail;
    }
}
