package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductUpdateRequestDTO {
    @NotBlank(message = "Product name cannot be blank!")
    private String name;

    @NotBlank(message = "Short description must not be blank!")
    private String shortDesc;

    @NotBlank(message = "Long description must not be blank")
    private String longDesc;

    private String thumbnail;

    private String categoryName;

    private Set<ProductSkuUpdateRequestDTO> productSku;
}
