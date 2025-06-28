package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.response.ProductResponseDTO;
import com.minhhai.ecommercebe.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mapping(source = "category", target = "categoryName", qualifiedByName = "toCategoryName")
    ProductResponseDTO toResponseDTO(Product entity);
}
