package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductSkuRequestDTO implements Serializable {
    @NotBlank(message = "Size must not be blank")
    private String size;

    @NotBlank(message = "Color must not be blank")
    private String color;

    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0!")
    private BigDecimal priceEach;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0!")
    private Integer quantity;
}
