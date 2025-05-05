package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.AddressRequestDTO;
import com.minhhai.ecommercebe.dto.response.AddressResponseDTO;
import com.minhhai.ecommercebe.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressRequestDTO dto);

    AddressResponseDTO toResponseDTO(Address entity);
}
