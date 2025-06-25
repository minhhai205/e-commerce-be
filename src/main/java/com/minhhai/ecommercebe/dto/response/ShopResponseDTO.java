package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.ShopStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ShopResponseDTO implements Serializable {
    Integer id;

    String name;

    String description;

    private String urlAvatar;

    private ShopStatus shopStatus;
}
