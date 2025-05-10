package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @NotBlank(message = "username must be not null")
    private String username;

    @NotBlank(message = "username must be not blank")
    private String password;
}
