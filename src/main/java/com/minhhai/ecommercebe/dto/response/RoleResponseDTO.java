package com.minhhai.ecommercebe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class RoleResponseDTO implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private Set<PermissionResponseDTO> permissions;
}
