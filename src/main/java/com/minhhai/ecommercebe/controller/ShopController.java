package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.ShopRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
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

import java.util.List;

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


    @PostMapping("/shop/my-shop/create")
    @Operation(method = "POST", summary = "Seller create new shop", description = "Send a request via this API to create new shop")
    public ApiSuccessResponse<ShopResponseDTO> registerMyShop(
            @Valid @RequestBody ShopRequestDTO shopRequestDTO
    ) {
        return ApiSuccessResponse.<ShopResponseDTO>builder()
                .data(shopService.sellerRegisterShop(shopRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create shop successfully!")
                .build();
    }

}
