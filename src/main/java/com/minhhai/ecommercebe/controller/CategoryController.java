package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.CategoryRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.CategoryResponseDTO;
import com.minhhai.ecommercebe.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Category Controller")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category/create")
    @Operation(method = "POST", summary = "Create new category", description = "Send a request via this API to create new category")
    @PreAuthorize("hasAnyAuthority({'create_category'})")
    public ApiSuccessResponse<CategoryResponseDTO> createCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return ApiSuccessResponse.<CategoryResponseDTO>builder()
                .data(categoryService.createCategory(categoryRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create category successfully!")
                .build();
    }

    @GetMapping("/category")
    @Operation(method = "GET", summary = "Get all category", description = "Send a request via this API to get all category")
    public ApiSuccessResponse<List<CategoryResponseDTO>> getAllCategory() {
        return ApiSuccessResponse.<List<CategoryResponseDTO>>builder()
                .data(categoryService.getAllCategory())
                .status(HttpStatus.OK.value())
                .message("Get all category successfully!")
                .build();
    }

    @PatchMapping("/category/update/{categoryId}")
    @Operation(method = "PATCH", summary = "Update category by id", description = "Send a request via this API to update category")
    @PreAuthorize("hasAnyAuthority('update_category')")
    public ApiSuccessResponse<CategoryResponseDTO> updateCategory(
            @PathVariable @Min(value = 1, message = "Category id must be greater than 0") int categoryId,
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO
    ) {
        return ApiSuccessResponse.<CategoryResponseDTO>builder()
                .data(categoryService.updateCategory(categoryId, categoryRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update category successfully!")
                .build();
    }
}
