package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.ProductSkuRequestDTO;
import com.minhhai.ecommercebe.dto.response.ProductSkuResponseDTO;
import com.minhhai.ecommercebe.model.ProductSku;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductSkuMapper {
    ProductSkuResponseDTO toResponseDTO(ProductSku productSku);

    ProductSku toEntity(ProductSkuRequestDTO productSkuRequestDTO);
}
