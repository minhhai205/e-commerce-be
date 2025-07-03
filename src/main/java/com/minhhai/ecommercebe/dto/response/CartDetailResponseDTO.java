package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CartDetailResponseDTO implements Serializable {
    private Long id;

    private long quantity;

    private ProductSkuResponseDTO productSku;
}
