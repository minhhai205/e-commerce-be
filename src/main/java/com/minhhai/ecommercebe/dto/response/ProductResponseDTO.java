package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ProductResponseDTO implements Serializable {
    private Long id;

    private String name;

    private String shortDesc;

    private String longDesc;

    private String thumbnail;

    private String categoryName;
}
