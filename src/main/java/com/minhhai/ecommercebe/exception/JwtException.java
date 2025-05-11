package com.minhhai.ecommercebe.exception;

import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtException extends AuthenticationException {

    private final ErrorCode errorCode;

    public JwtException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}