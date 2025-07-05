package com.minhhai.ecommercebe.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderDetailResponseDTO {
    private Long id;

    private long quantity;

    private BigDecimal priceEach;

    private ProductSkuResponseDTO productSku;
}
