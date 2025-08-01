package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.ShopRequestDTO;
import com.minhhai.ecommercebe.dto.response.ShopDetailResponseDTO;
import com.minhhai.ecommercebe.dto.response.ShopResponseDTO;
import com.minhhai.ecommercebe.model.Shop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ShopMapper {
    Shop toEntity(ShopRequestDTO dto);

    ShopResponseDTO toResponseDTO(Shop entity);

    ShopDetailResponseDTO toDetailResponseDTO(Shop entity);
}
