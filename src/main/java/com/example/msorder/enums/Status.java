package com.example.msorder.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum Status {
    ABANDONED("Abandoned"),
    INCOMPLETE("Incomplete");

    private final String value;

    public String getValue() {
        return value.toUpperCase();
    }
}
