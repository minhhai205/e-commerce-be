package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.ApiResponse.PageResponse;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Tag(name = "User Controller")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/user/create")
    @Operation(method = "POST", summary = "Add new user", description = "Send a request via this API to create new user")
    @PreAuthorize("hasAnyAuthority('create_user')")
    public ApiSuccessResponse<Long> addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        long userId = userService.createUser(userDTO);

        return ApiSuccessResponse.<Long>builder()
                .data(userId)
                .status(HttpStatus.OK.value())
                .message("Create user successfully!")
                .build();
    }

    @PatchMapping("/user/update/{id}")
    @Operation(method = "PATCH", summary = "Update user", description = "Send a request via this API to update user")
    @PreAuthorize("hasAnyAuthority('update_user')")
    public ApiSuccessResponse<UserResponseDTO> updateUser(
            @PathVariable @Min(value = 1, message = "User id must be greater than 0") long id,
            @Valid @RequestBody UserRequestDTO userDTO) {

        return ApiSuccessResponse.<UserResponseDTO>builder()
                .data(userService.updateUser(id, userDTO))
                .status(HttpStatus.OK.value())
                .message("Update user successfully!")
                .build();
    }

    @GetMapping("/user")
    @Operation(method = "GET", summary = "Get all user with pagination, search and filter", description = "Send a request via this API to get all user")
    @PreAuthorize("hasAnyAuthority('view_user')")
    public ApiSuccessResponse<PageResponse<List<UserResponseDTO>>> getAllUser(
            Pageable pageable,
            @RequestParam(required = false) String[] filters
    ) {
        return ApiSuccessResponse.<PageResponse<List<UserResponseDTO>>>builder()
                .data(userService.getAllUsers(pageable, filters))
                .status(HttpStatus.OK.value())
                .message("Get users successfully!")
                .build();
    }
}
