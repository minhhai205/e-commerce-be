package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.response.CartResponseDTO;
import com.minhhai.ecommercebe.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartDetailMapper.class})
public interface CartMapper {
    CartResponseDTO toResponseDTO(Cart entity);
}
