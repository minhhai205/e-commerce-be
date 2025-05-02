package com.minhhai.ecommercebe.util.enums;

public enum OrderStatus {
    PENDING,         // Đơn vừa tạo, chờ thanh toán/duyệt
    CONFIRMED,       // Đã xác nhận đơn
    SHIPPED,         // Đã giao cho đơn vị vận chuyển
    DELIVERED,       // Đã giao thành công
    CANCELLED        // Bị huỷ
}
