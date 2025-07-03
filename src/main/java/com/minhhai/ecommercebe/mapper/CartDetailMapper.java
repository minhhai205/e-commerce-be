package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.response.CartDetailResponseDTO;
import com.minhhai.ecommercebe.model.CartDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductSkuMapper.class})
public interface CartDetailMapper {
    CartDetailResponseDTO toResponseDTO(CartDetail entity);
}
