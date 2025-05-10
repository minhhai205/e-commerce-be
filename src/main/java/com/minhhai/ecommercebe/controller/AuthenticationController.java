package com.minhhai.ecommercebe.controller;


import com.minhhai.ecommercebe.dto.request.LoginRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.TokenResponseDTO;
import com.minhhai.ecommercebe.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    public ApiSuccessResponse<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ApiSuccessResponse.<TokenResponseDTO>builder()
                .data(authenticationService.authenticate(loginRequestDTO))
                .message("Authenticated!")
                .status(HttpStatus.OK.value())
                .build();
    }
}
