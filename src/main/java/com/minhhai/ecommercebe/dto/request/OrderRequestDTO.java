package com.minhhai.ecommercebe.dto.request;


import jakarta.validation.constraints.NotBlank;
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
}
