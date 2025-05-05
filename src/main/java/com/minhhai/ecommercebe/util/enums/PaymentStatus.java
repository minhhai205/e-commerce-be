package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {
    UNPAID,
    PAID,
    UNKNOWN;

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return PaymentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
