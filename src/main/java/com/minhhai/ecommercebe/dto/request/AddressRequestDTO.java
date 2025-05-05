package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {
    @NotBlank(message = "Detailed address cannot be left blank!")
    private String detail;

    @NotBlank(message = "District cannot be left blank!")
    private String district;

    @NotBlank(message = "Province cannot be left blank!")
    private String province;

    @NotBlank(message = "Country cannot be left blank!")
    private String country;
}
