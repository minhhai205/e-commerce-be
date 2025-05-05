package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    @JsonCreator
    public static Gender fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return Gender.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

}

