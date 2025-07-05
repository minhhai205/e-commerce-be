package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.response.OrderDetailResponseDTO;
import com.minhhai.ecommercebe.model.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductSkuMapper.class})
public interface OrderDetailMapper {
    OrderDetailResponseDTO toResponseDTO(OrderDetail orderDetail);
}
