package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiResponse;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "User Controller")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/user/create")
    @Operation(method = "POST", summary = "Add new user", description = "Send a request via this API to create new user")
    public ApiResponse addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        long userId = userService.saveUser(userDTO);

        return ApiSuccessResponse.<Long>builder()
                .data(userId)
                .status(HttpStatus.OK.value())
                .message("Create user successfully!")
                .build();
    }
}
