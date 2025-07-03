package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDetailResponseDTO {
    private long quantity;

    private ProductSkuResponseDTO productSku;
}
