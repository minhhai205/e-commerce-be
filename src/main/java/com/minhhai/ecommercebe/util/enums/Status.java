package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE,
    INACTIVE,
    LOCKED,
    UNKNOWN;

    @JsonCreator
    public static Status fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Status.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
