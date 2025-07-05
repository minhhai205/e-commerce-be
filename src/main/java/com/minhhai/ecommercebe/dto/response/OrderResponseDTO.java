package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.util.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class OrderResponseDTO implements Serializable {
    private Long id;

    private String receiverAddress;

    private String receiverPhoneNumber;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private Set<OrderDetailResponseDTO> orderDetails;

//    private Payment payment;

}
