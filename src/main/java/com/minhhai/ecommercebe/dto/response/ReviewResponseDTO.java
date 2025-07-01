package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ReviewResponseDTO implements Serializable {
    private Integer rate;

    private String description;

    private Long userId;
}
