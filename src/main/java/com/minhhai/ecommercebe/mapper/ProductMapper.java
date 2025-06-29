package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.ProductRequestDTO;
import com.minhhai.ecommercebe.dto.response.ProductDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ProductResponseDTO;
import com.minhhai.ecommercebe.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ProductSkuMapper.class})
public interface ProductMapper {
    @Mapping(source = "category", target = "categoryName", qualifiedByName = "toCategoryName")
    ProductResponseDTO toResponseDTO(Product entity);

    @Mapping(source = "category", target = "categoryName", qualifiedByName = "toCategoryName")
    ProductDetailResponseDTO toDetailResponseDTO(Product entity);

    Product toEntity(ProductRequestDTO dto);
}
