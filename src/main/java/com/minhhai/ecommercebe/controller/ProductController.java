package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.ProductRequestDTO;
import com.minhhai.ecommercebe.dto.request.ProductUpdateRequestDTO;
import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.ApiResponse.PageResponse;
import com.minhhai.ecommercebe.dto.response.ProductDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ProductResponseDTO;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.service.ProductService;
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
@Tag(name = "Product Controller")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product/create")
    @Operation(method = "POST", summary = "Create new user", description = "Send a request via this API to create new product")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<ProductDetailResponseDTO> createNewProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return ApiSuccessResponse.<ProductDetailResponseDTO>builder()
                .data(productService.createNewProduct(productRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create user successfully!")
                .build();
    }

    @PatchMapping("/product/update/{productId}")
    @Operation(method = "PATCH", summary = "Update product", description = "Send a request via this API to update product")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<ProductDetailResponseDTO> updateProduct(
            @PathVariable @Min(value = 1, message = "Product id must be greater than 0") long productId,
            @Valid @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {

        return ApiSuccessResponse.<ProductDetailResponseDTO>builder()
                .data(productService.updateProduct(productId, productUpdateRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update product successfully!")
                .build();
    }

    @GetMapping("/product/{productId}")
    @Operation(method = "GET", summary = "Get product detail", description = "Send a request via this API to get product")
    public ApiSuccessResponse<ProductDetailResponseDTO> getProductDetail(
            @PathVariable @Min(value = 1, message = "Product id must be greater than 0") long productId
    ) {
        return ApiSuccessResponse.<ProductDetailResponseDTO>builder()
                .data(productService.getProductDetailByProductId(productId))
                .status(HttpStatus.OK.value())
                .message("Get product successfully!")
                .build();
    }

//    @GetMapping("/product")
//    @Operation(method = "GET", summary = "Get all product with pagination, search and filter", description = "Send a request via this API to get all product")
//    public ApiSuccessResponse<PageResponse<List<ProductResponseDTO>>> getAllProducts(
//            Pageable pageable,
//            @RequestParam(required = false) String[] filters
//    ) {
//        return ApiSuccessResponse.<PageResponse<List<ProductResponseDTO>>>builder()
//                .data(productService.getAllProducts(pageable, filters))
//                .status(HttpStatus.OK.value())
//                .message("Get products successfully!")
//                .build();
//    }
}
