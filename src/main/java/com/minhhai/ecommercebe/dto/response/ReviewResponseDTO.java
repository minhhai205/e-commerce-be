package com.minhhai.ecommercebe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ReviewResponseDTO implements Serializable {
    private Long id;

    private Integer rate;

    private String description;

    private Long userId;
}
