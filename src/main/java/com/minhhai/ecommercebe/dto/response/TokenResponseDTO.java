package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class TokenResponseDTO implements Serializable {
    private String accessToken;

    private String refreshToken;

    private Long userId;
}
