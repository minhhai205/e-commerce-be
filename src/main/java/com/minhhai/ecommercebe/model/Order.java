package com.minhhai.ecommercebe.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AbstractEntity<Long> {

    private String receiverAddress;

    private String receiverNumber;

    @Min(value = 0, message = "value must be greater than 0")
    private double totalPrice;

//    @NotNull(message = "status must be required")
//    private OrderStatus status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Date is required")
    private Date orderAt;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order")
    private Payment payment;
}