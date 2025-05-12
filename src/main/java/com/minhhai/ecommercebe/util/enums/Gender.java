package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    @JsonCreator
    public static Gender fromString(String value) {
        return EnumUtils.fromString(Gender.class, value, UNKNOWN);
    }

}

