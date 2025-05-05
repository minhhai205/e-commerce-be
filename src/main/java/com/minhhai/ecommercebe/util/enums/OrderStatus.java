package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    PENDING,         // Đơn vừa tạo, chờ thanh toán/duyệt
    CONFIRMED,       // Đã xác nhận đơn
    SHIPPED,         // Đã giao cho đơn vị vận chuyển
    DELIVERED,       // Đã giao thành công
    CANCELLED,        // Bị huỷ
    UNKNOWN;

    @JsonCreator
    public static OrderStatus fromString(String value) {
        if (value == null) return UNKNOWN;
        try {
            return OrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
