package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CategoryResponseDTO implements Serializable {
    private Integer id;

    private String name;

    private String description;
}
