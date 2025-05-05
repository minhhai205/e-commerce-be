package com.minhhai.ecommercebe.dto.response.ApiResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class ApiSuccessResponse<T> extends ApiResponse {
    private T data;
}