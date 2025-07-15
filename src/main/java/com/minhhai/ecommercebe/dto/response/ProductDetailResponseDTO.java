package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.dto.request.ProductSkuRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class ProductDetailResponseDTO implements Serializable {
    private Long id;

    private String name;

    private String shortDesc;

    private String longDesc;

    private String thumbnail;

    private String categoryName;

    private Set<ProductSkuRequestDTO> productSku;
}
