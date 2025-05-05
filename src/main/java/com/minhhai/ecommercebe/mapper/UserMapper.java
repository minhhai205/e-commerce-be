package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface UserMapper {
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User entity);
}