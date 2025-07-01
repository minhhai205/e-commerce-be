package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.ReviewRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.ApiResponse.PageResponse;
import com.minhhai.ecommercebe.dto.response.ReviewResponseDTO;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Tag(name = "Review Controller")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/review/product/{productId}")
    @Operation(method = "GET", summary = "Get all product reviews", description = "Send a request via this API to get all product reviews")
    public ApiSuccessResponse<List<ReviewResponseDTO>> getAllProductReviews(
            @PathVariable @Min(value = 1, message = "Product id must be greater than 0") long productId
    ) {
        return ApiSuccessResponse.<List<ReviewResponseDTO>>builder()
                .data(reviewService.getAllProductReviews(productId))
                .status(HttpStatus.OK.value())
                .message("Get all product reviews successfully!")
                .build();
    }

    @PostMapping("/review/create")
    @Operation(method = "POST", summary = "Create new review product", description = "Send a request via this API to create new review")
    public ApiSuccessResponse<ReviewResponseDTO> createNewReview(
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO
    ) {
        return ApiSuccessResponse.<ReviewResponseDTO>builder()
                .data(reviewService.createNewReview(reviewRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create new review successfully!")
                .build();
    }

    @PatchMapping("/review/update/{id}")
    @Operation(method = "PATCH", summary = "Update review", description = "Send a request via this API to update review")
    public ApiSuccessResponse<ReviewResponseDTO> updateReview(
            @PathVariable @Min(value = 1, message = "Review id must be greater than 0") long id,
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {

        return ApiSuccessResponse.<ReviewResponseDTO>builder()
                .data(reviewService.updateReview(id, reviewRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update review successfully!")
                .build();
    }

    @DeleteMapping("/review/delete/{id}")
    @Operation(method = "DELETE", summary = "Delete review", description = "Send a request via this API to delete review")
    public ApiSuccessResponse<Long> deleteReview(
            @PathVariable @Min(value = 1, message = "Review id must be greater than 0") long id
    ) {
        return ApiSuccessResponse.<Long>builder()
                .data(reviewService.deleteReview(id))
                .status(HttpStatus.OK.value())
                .message("Delete review successfully!")
                .build();
    }
}
