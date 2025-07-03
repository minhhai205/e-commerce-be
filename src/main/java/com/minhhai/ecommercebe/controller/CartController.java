package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.CartRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.CartResponseDTO;
import com.minhhai.ecommercebe.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "Cart Controller")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/create")
    public ApiSuccessResponse<CartResponseDTO> addToCart(@Valid @RequestBody CartRequestDTO cartRequestDTO) {
        return ApiSuccessResponse.<CartResponseDTO>builder()
                .data(cartService.addToCart(cartRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Add to card successfully!")
                .build();
    }

    @PatchMapping("/cart/update")
    public ApiSuccessResponse<CartResponseDTO> updateCart(@Valid @RequestBody CartRequestDTO cartRequestDTO) {
        return ApiSuccessResponse.<CartResponseDTO>builder()
                .data(cartService.updateCart(cartRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update card successfully!")
                .build();
    }

    @DeleteMapping("/cart/delete")
    public ApiSuccessResponse<CartResponseDTO> deleteCartItem(@Valid @RequestBody CartRequestDTO cartRequestDTO) {
        return ApiSuccessResponse.<CartResponseDTO>builder()
                .data(cartService.deleteCartItem(cartRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Delete cart item successfully!")
                .build();
    }

    @GetMapping("/cart")
    public ApiSuccessResponse<CartResponseDTO> getCart() {
        return ApiSuccessResponse.<CartResponseDTO>builder()
                .data(cartService.getCart())
                .status(HttpStatus.OK.value())
                .message("Get cart successfully!")
                .build();
    }
}
