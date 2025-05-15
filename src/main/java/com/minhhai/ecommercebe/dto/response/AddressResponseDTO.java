package com.minhhai.ecommercebe.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class AddressResponseDTO implements Serializable {
    private String detail;

    private String district;

    private String province;

    private String country;
}
