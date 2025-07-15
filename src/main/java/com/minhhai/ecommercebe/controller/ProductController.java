package com.minhhai.ecommercebe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhhai.ecommercebe.dto.request.ProductRequestDTO;
import com.minhhai.ecommercebe.dto.request.ProductUpdateRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.ProductDetailResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.service.ProductService;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Validated
@Tag(name = "Product Controller")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PostMapping("/product/create")
    @Operation(method = "POST", summary = "Create new product", description = "Send a request via this API to create new product")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<ProductDetailResponseDTO> createNewProduct(
            @RequestParam("productRequestDTO") String productRequestDTO,
            @RequestParam("fileThumbnail") MultipartFile fileThumbnail
    ) {
        try {
            ProductRequestDTO dto = objectMapper.readValue(productRequestDTO, ProductRequestDTO.class);
            return ApiSuccessResponse.<ProductDetailResponseDTO>builder()
                    .data(productService.createNewProduct(dto, fileThumbnail))
                    .status(HttpStatus.OK.value())
                    .message("Create new product successfully!")
                    .build();
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorCode.JSON_INVALID);
        }
    }

    @PatchMapping("/product/update/{productId}")
    @Operation(method = "PATCH", summary = "Update product", description = "Send a request via this API to update product")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<ProductDetailResponseDTO> updateProduct(
            @PathVariable @Min(value = 1, message = "Product id must be greater than 0") long productId,
            @RequestParam("productUpdateRequestDTO") String productUpdateRequestDTO,
            @RequestParam("fileThumbnail") MultipartFile fileThumbnail
    ) {
        try {
            ProductUpdateRequestDTO dto = objectMapper.readValue(productUpdateRequestDTO, ProductUpdateRequestDTO.class);
            return ApiSuccessResponse.<ProductDetailResponseDTO>builder()
                    .data(productService.updateProduct(productId, dto, fileThumbnail))
                    .status(HttpStatus.OK.value())
                    .message("Update product successfully!")
                    .build();
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorCode.JSON_INVALID);
        }
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
