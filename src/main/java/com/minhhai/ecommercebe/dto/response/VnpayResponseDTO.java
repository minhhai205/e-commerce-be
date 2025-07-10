package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class VnpayResponseDTO implements Serializable {
    private String paymentUrl;
}
