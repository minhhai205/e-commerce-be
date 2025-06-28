package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.model.User;
import com.minhhai.ecommercebe.util.enums.ShopStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class ShopDetailResponseDTO implements Serializable {
    private Integer id;

    private String name;

    private String description;

    private String urlAvatar;

    private ShopStatus shopStatus;

    private Set<ProductResponseDTO> products;

}
