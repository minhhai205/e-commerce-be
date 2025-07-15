package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductSkuResponseDTO implements Serializable {
    private Long id;

    private String size;

    private String color;

    private BigDecimal priceEach;

    private Integer quantity;
}
