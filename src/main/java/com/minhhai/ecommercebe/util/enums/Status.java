package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE,
    INACTIVE,
    LOCKED,
    UNKNOWN;

    @JsonCreator
    public static Status fromString(String value) {
        return EnumUtils.fromString(Status.class, value, UNKNOWN);
    }
}
