package com.minhhai.ecommercebe.service;

import com.minhhai.ecommercebe.dto.request.RoleRequestDTO;
import com.minhhai.ecommercebe.dto.response.RoleResponseDTO;
import com.minhhai.ecommercebe.exception.AppException;
import com.minhhai.ecommercebe.mapper.RoleMapper;
import com.minhhai.ecommercebe.model.Permission;
import com.minhhai.ecommercebe.model.Role;
import com.minhhai.ecommercebe.repository.PermissionRepository;
import com.minhhai.ecommercebe.repository.RoleRepository;
import com.minhhai.ecommercebe.util.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public Integer saveRole(RoleRequestDTO roleRequestDTO) {

        if (roleRepository.existsByName(roleRequestDTO.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        Role role = Role.builder()
                .name(roleRequestDTO.getName())
                .description(roleRequestDTO.getDescription())
                .permissions(getPermissions(roleRequestDTO))
                .build();

        roleRepository.save(role);

        return role.getId();
    }


    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(roleMapper::toResponseDTO).collect(Collectors.toList());
    }

    public Integer updateRole(Integer id, RoleRequestDTO roleRequestDTO) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        if (!role.getName().equals(roleRequestDTO.getName())
                && roleRepository.existsByName(roleRequestDTO.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        role.setName(roleRequestDTO.getName());
        role.setDescription(roleRequestDTO.getDescription());
        role.setPermissions(getPermissions(roleRequestDTO));

        roleRepository.save(role);

        return role.getId();
    }

    private Set<Permission> getPermissions(RoleRequestDTO roleRequestDTO) {
        List<String> permissionNames = new ArrayList<>();

        roleRequestDTO.getPermissions().forEach(p -> permissionNames.add(p.getName()));

        Set<Permission> permissions = permissionRepository.findByNameIn(permissionNames);

        if (permissionNames.size() != permissions.size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }

        return permissions;
    }
}
