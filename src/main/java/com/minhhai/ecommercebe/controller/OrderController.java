package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.request.OrderRequestDTO;
import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.OrderResponseDTO;
import com.minhhai.ecommercebe.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@Tag(name = "Order Controller")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/create")
    public ApiSuccessResponse<List<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return ApiSuccessResponse.<List<OrderResponseDTO>>builder()
                .data(orderService.createOrder(orderRequestDTO))
                .status(HttpStatus.OK.value())
                .message("Create order successfully!")
                .build();
    }
}
