package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryRequestDTO implements Serializable {
    @NotBlank(message = "Category name cannot be blank!")
    private String name;

    private String description;

}
