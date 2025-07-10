package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.configuration.VnpayConfig;
import com.minhhai.ecommercebe.dto.response.VnpayResponseDTO;
import com.minhhai.ecommercebe.dto.response.VnpayStatusResponse;
import com.minhhai.ecommercebe.util.commons.VnpayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final VnpayConfig vnpayConfig;

    public VnpayResponseDTO createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode"); // môi trường test luôn là NCB

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VnpayUtil.getIpAddress(request));

        //build query url
        String queryUrl = VnpayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnpayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnpayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VnpayResponseDTO.builder()
                .paymentUrl(paymentUrl).build();
    }

    public VnpayStatusResponse payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        return VnpayStatusResponse.builder()
                .status(status)
                .message(VnpayUtil.VNPAY_RESPONSE_MESSAGES.get(status))
                .build();
    }
}
