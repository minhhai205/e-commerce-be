package com.minhhai.ecommercebe.util.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_EXISTED(409, "User existed", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(401, "User not existed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(401, "User unauthorized", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),
    TOKEN_TYPE_INVALID(400, "Token type invalid", HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_INVALID(401, "Access token invalid", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(401, "Refresh token invalid", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_BLACK_LIST(401, "Access token black list", HttpStatus.UNAUTHORIZED),
    ROLE_EXISTED(409, "Role existed", HttpStatus.CONFLICT),
    ROLE_NOT_EXISTED(400, "Role not existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(400, "Permission not existed", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(409, "Username existed", HttpStatus.CONFLICT),
    EMAIL_EXISTED(409, "Email existed", HttpStatus.CONFLICT),
    CATEGORY_EXISTED(409, "Category existed", HttpStatus.CONFLICT),
    SELLER_ALREADY_HAS_SHOP(409, "Seller already has shop", HttpStatus.CONFLICT),
    AUTHORITY_NOT_SUPPORTED(400, "Authority not supported", HttpStatus.BAD_REQUEST),
    SHOP_NOT_EXISTED(400, "Shop not existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(400, "Product not existed", HttpStatus.BAD_REQUEST),
    PRODUCT_SKU_NOT_EXISTED(400, "Product sku not existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(400, "Category not existed", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}