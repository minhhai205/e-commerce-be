package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentStatus {
    UNPAID,
    PAID,
    UNKNOWN;

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        return EnumUtils.fromString(PaymentStatus.class, value, UNKNOWN);
    }
}
