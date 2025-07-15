package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.OrderRequestDTO;
import com.minhhai.ecommercebe.dto.request.OrderUpdateRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.OrderResponseDTO;
import com.minhhai.ecommercebe.service.OrderService;
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
@Tag(name = "Order Controller")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/create")
    @PreAuthorize("hasAnyRole({'CUSTOMER'})")
    public ApiSuccessResponse<List<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return ApiSuccessResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.createOrder(orderRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create order successfully!")
                .build();
    }

    @GetMapping("/order/my-order")
    @PreAuthorize("hasAnyRole({'CUSTOMER'})")
    public ApiSuccessResponse<List<OrderResponseDTO>> viewAllMyOrder() {
        return ApiSuccessResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.viewAllMyOrder())
                .status(HttpStatus.OK.value())
                .message("Get all order successfully!")
                .build();
    }

    @GetMapping("/order/shop")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<List<OrderResponseDTO>> viewAllShopOrder() {
        return ApiSuccessResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.viewAllShopOrder())
                .status(HttpStatus.OK.value())
                .message("Get all order successfully!")
                .build();
    }

    @PatchMapping("/order/my-order/cancel/{orderId}")
    @PreAuthorize("hasAnyRole({'CUSTOMER'})")
    public ApiSuccessResponse<Long> userCancelOrder(
            @PathVariable @Min(value = 1, message = "Order id must be greater than 0") long orderId
    ) {
        return ApiSuccessResponse.<Long>builder()
                .data(orderService.userCancelOrder(orderId))
                .status(HttpStatus.OK.value())
                .message("Cancel order successfully!")
                .build();
    }

    @PatchMapping("/order/shop/update/{orderId}")
    @PreAuthorize("hasAnyRole({'SELLER'})")
    public ApiSuccessResponse<Long> shopUpdateOrder(
            @PathVariable @Min(value = 1, message = "Order id must be greater than 0") long orderId,
            @Valid @RequestBody OrderUpdateRequestDTO orderUpdateRequestDTO
    ) {
        return ApiSuccessResponse.<Long>builder()
                .data(orderService.shopUpdateOrder(orderId, orderUpdateRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Update order successfully!")
                .build();
    }

}
