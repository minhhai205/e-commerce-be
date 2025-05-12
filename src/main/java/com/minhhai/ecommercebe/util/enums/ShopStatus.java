package com.minhhai.ecommercebe.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShopStatus {
    PENDING,        //  Shop vừa đăng ký, đang chờ admin duyệt
    APPROVED,       //  Shop đã được duyệt, có thể hoạt động
    REJECTED,       //  Bị từ chối do thông tin không hợp lệ
    DEACTIVATED,    // 	Chủ shop tự tạm dừng hoạt động
    DELETED,        //  Đã bị xóa khỏi hệ thống
    UNKNOWN;

    @JsonCreator
    public static ShopStatus fromString(String value) {
        return EnumUtils.fromString(ShopStatus.class, value, UNKNOWN);
    }
}
