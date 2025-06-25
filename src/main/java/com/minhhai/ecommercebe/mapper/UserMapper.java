package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.UserRequestDTO;
import com.minhhai.ecommercebe.dto.response.UserResponseDTO;
import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, RoleMapper.class})
public interface UserMapper {
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "roleNames", source = "roles", qualifiedByName = "toRoleName")
    UserResponseDTO toResponseDTO(User entity);
}