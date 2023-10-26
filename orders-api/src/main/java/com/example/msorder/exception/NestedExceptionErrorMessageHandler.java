package com.example.msorder.exception;

import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.NestedRuntimeException;

@Named
public class NestedExceptionErrorMessageHandler {

    private NestedExceptionErrorMessageHandler() {
    }

    public <T extends NestedRuntimeException> String messageFromNestedException(T t) {
        var rootCause = t.getRootCause();
        if (rootCause instanceof ConstraintViolationException constraintViolationException) {
            return fromConstraintViolationException(constraintViolationException);
        }
        return null;
    }

    private String fromConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst()
                .orElse("");
    }

}

