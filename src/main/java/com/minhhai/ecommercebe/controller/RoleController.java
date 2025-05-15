package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.RoleRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.RoleResponseDTO;
import com.minhhai.ecommercebe.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Tag(name = "Role Controller")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    public final RoleService roleService;

    @GetMapping("/role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiSuccessResponse<List<RoleResponseDTO>> getAllRoles() {
        return ApiSuccessResponse.<List<RoleResponseDTO>>builder()
                .data(roleService.getAllRoles())
                .status(HttpStatus.OK.value())
                .message("Get all roles successfully!")
                .build();
    }

    @PostMapping("/role/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiSuccessResponse<Integer> createRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        return ApiSuccessResponse.<Integer>builder()
                .data(roleService.saveRole(roleRequestDTO))
                .status(HttpStatus.CREATED.value())
                .message("Role created successfully!")
                .build();
    }

    @PatchMapping("/role/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiSuccessResponse<Integer> updateRole(
            @PathVariable("id") @Min(value = 1, message = "Role id must be greater than 0") int id,
            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        return ApiSuccessResponse.<Integer>builder()
                .data(roleService.updateRole(id, roleRequestDTO))
                .status(HttpStatus.ACCEPTED.value())
                .message("Role updated successfully!")
                .build();
    }

}
