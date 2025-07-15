package com.minhhai.ecommercebe.controller;

import com.minhhai.ecommercebe.dto.response.ApiResponse.ApiSuccessResponse;
import com.minhhai.ecommercebe.dto.response.VnpayResponseDTO;
import com.minhhai.ecommercebe.dto.response.VnpayStatusResponse;
import com.minhhai.ecommercebe.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "Payment Controller")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/vn-pay")
    public ApiSuccessResponse<VnpayResponseDTO> createVnPayPayment(HttpServletRequest request) {
        return ApiSuccessResponse.<VnpayResponseDTO>builder()
                .data(paymentService.createVnPayPayment(request))
                .status(HttpStatus.OK.value())
                .message("Create Vnpay payment successfully!")
                .build();
    }

    @GetMapping("/payment/vn-pay-callback")
    public ApiSuccessResponse<VnpayStatusResponse> payCallbackHandler(HttpServletRequest request) {
        return ApiSuccessResponse.<VnpayStatusResponse>builder()
                .data(paymentService.payCallbackHandler(request))
                .status(HttpStatus.OK.value())
                .message("Create Vnpay payment successfully!")
                .build();
    }
}
