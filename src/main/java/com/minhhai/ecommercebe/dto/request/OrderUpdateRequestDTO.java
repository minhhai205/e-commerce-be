package com.minhhai.ecommercebe.dto.request;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderUpdateRequestDTO implements Serializable {
    @NotNull(message = "Order status must not be blank!")
    @EnumPattern(name = "Order status", regexp = "PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED")
    private OrderStatus status;
}
