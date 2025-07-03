package com.minhhai.ecommercebe.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartRequestDTO implements Serializable {
    @Min(value = 1, message = "Quantity must be at least 1!")
    private long quantity;

    @NotNull(message = "Product sku id must not be blank!")
    private Long productSkuId;
}
