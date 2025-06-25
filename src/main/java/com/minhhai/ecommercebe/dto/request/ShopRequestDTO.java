package com.minhhai.ecommercebe.dto.request;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ShopRequestDTO implements Serializable {
    @NotBlank(message = "Shop name must not be blank!")
    String name;

    @NotBlank(message = "Shop description must not be blank!")
    String description;

    private String urlAvatar;

    @EnumPattern(name = "Shop status", regexp = "PENDING|APPROVED|REJECTED|DEACTIVATED|DELETED")
    private ShopStatus shopStatus;
}
