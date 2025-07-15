package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.ShopRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.ShopDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ShopResponseDTO;
import com.minhhai.ecommercebe.service.ShopService;
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

@RestController
@Validated
@Tag(name = "Shop Controller")
@RequiredArgsConstructor
@Slf4j
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/shop/create")
    @Operation(method = "POST", summary = "Admin create new shop for seller", description = "Send a request via this API to create new shop")
    @PreAuthorize("hasAnyAuthority('create_shop')")
    public ApiSuccessResponse<ShopResponseDTO> createNewShopForSeller(
            @Valid @RequestBody ShopRequestDTO shopRequestDTO,
            @RequestParam(required = true) long userId
    ) {
        return ApiSuccessResponse.<ShopResponseDTO>builder()
                .data(shopService.adminCreateShop(userId, shopRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create shop successfully!")
                .build();
    }

    @GetMapping("/shop/{shopId}")
    @Operation(method = "GET", summary = "Get shop information", description = "Send a request via this API to get shop information")
    public ApiSuccessResponse<ShopDetailResponseDTO> getShopDetail(
            @PathVariable @Min(value = 1, message = "Shop id must be greater than 0") int shopId
    ) {
        return ApiSuccessResponse.<ShopDetailResponseDTO>builder()
                .data(shopService.getShopDetailByShopId(shopId))
                .status(HttpStatus.OK.value())
                .message("Get shop detail successfully!")
                .build();
    }

    @PatchMapping("/shop/update/{shopId}")
    @Operation(method = "PATCH", summary = "Admin update shop", description = "Send a request via this API to update shop")
    @PreAuthorize("hasAnyAuthority('update_shop')")
    public ApiSuccessResponse<ShopResponseDTO> updateShop(
            @PathVariable @Min(value = 1, message = "Shop id must be greater than 0") int shopId,
            @Valid @RequestBody ShopRequestDTO shopRequestDTO
    ) {
        return ApiSuccessResponse.<ShopResponseDTO>builder()
                .data(shopService.adminUpdateShop(shopId, shopRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update shop successfully!")
                .build();
    }


    @PostMapping("/shop/my-shop/create")
    @Operation(method = "POST", summary = "Seller create new shop", description = "Send a request via this API to create new shop")
    @PreAuthorize("hasAnyRole({'CUSTOMER', 'SELLER'})")
    public ApiSuccessResponse<ShopResponseDTO> registerMyShop(
            @Valid @RequestBody ShopRequestDTO shopRequestDTO
    ) {
        return ApiSuccessResponse.<ShopResponseDTO>builder()
                .data(shopService.sellerRegisterShop(shopRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create shop successfully!")
                .build();
    }

    @GetMapping("/shop/my-shop/detail")
    @Operation(method = "GET", summary = "Get my shop information", description = "Send a request via this API to get my shop information")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ApiSuccessResponse<ShopDetailResponseDTO> getMyShopDetail() {
        return ApiSuccessResponse.<ShopDetailResponseDTO>builder()
                .data(shopService.getMyShopDetail())
                .status(HttpStatus.OK.value())
                .message("Get shop detail successfully!")
                .build();
    }

    @PatchMapping("/shop/my-shop/update")
    @Operation(method = "PATCH", summary = "Seller update shop", description = "Send a request via this API to update shop")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<ShopResponseDTO> updateMyShop(
            @Valid @RequestBody ShopRequestDTO shopRequestDTO
    ) {
        return ApiSuccessResponse.<ShopResponseDTO>builder()
                .data(shopService.updateMyShop(shopRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update shop successfully!")
                .build();
    }
}
