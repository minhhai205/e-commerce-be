package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDTO {
    private String accessToken;

    private String refreshToken;

    private Long userId;
}
