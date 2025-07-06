package com.minhhai.ecommercebe.dto.request;


import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class OrderRequestDTO implements Serializable {
    @NotBlank(message = "Recipient address cannot be blank!")
    private String receiverAddress;

    @NotBlank(message = "Recipient phone number cannot be blank!")
    private String receiverPhoneNumber;

    private Set<Long> cartDetailIds;

    @NotNull(message = "Payment method is required!")
    @EnumPattern(name = "Payment method", regexp = "COD|VNPAY")
    private PaymentMethod paymentMethod;
}
