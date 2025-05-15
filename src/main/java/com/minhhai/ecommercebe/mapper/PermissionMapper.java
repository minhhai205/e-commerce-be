package com.minhhai.ecommercebe.mapper;

import com.minhhai.ecommercebe.dto.request.PermissionRequestDTO;
import com.minhhai.ecommercebe.dto.response.PermissionResponseDTO;
import com.minhhai.ecommercebe.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity(PermissionRequestDTO permissionRequestDTO);

    PermissionResponseDTO toResponseDTO(Permission permission);
}
