package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.RoleRequestDTO;
import com.minhhai.ecommercebe.dto.response.RoleResponseDTO;
import com.minhhai.ecommercebe.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    Role toEntity(RoleRequestDTO dto);

    RoleResponseDTO toResponseDTO(Role entity);

    @Named("toRoleName")
    default String toRoleName(Role role) {
        return role.getName();
    }
}
