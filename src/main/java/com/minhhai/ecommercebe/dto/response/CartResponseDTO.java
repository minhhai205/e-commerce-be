package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class CartResponseDTO implements Serializable {
    private Integer id;

    private Set<CartDetailResponseDTO> cartDetails;
}
