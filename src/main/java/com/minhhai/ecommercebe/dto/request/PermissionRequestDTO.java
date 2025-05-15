package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PermissionRequestDTO implements Serializable {
    @NotBlank(message = "Permission's name must be not blank!")
    private String name;

    private String description;
}
