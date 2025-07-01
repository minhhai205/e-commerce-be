package com.minhhai.ecommercebe.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReviewRequestDTO implements Serializable {
    @Min(value = 1, message = "Rate must be greater than or equal to 1!")
    @Max(value = 5, message = "Rate must be less than or equal to 5!")
    @NotNull(message = "Rate must not be blank!")
    private Integer rate;

    private String description;

    @NotNull(message = "Product id must not be blank!")
    private Long productId;
}
