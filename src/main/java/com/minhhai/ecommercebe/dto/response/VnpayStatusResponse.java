package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class VnpayStatusResponse implements Serializable {
    private String status;
    private String message;
}
