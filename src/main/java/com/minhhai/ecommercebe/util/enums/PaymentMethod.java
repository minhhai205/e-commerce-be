package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethod {
    COD,
    VNPAY,
    UNKNOWN;

    @JsonCreator
    public static PaymentMethod fromString(String value) {
        return EnumUtils.fromString(PaymentMethod.class, value, UNKNOWN);
    }
}
