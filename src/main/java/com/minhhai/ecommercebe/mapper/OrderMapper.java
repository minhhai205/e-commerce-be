package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.response.OrderResponseDTO;
import com.minhhai.ecommercebe.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {
    OrderResponseDTO toResponseDTO(Order order);
}
