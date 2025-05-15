package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO {
    @NotBlank(message = "Role's name must not be blank!")
    private String name;

    private String description;

    @NotNull(message = "Permissions must not be blank!")
    private List<PermissionRequestDTO> permissions;
}
