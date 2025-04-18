package com.minhhai.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractEntity<Long> {

    @Column(name = "price")
    @Min(value = 0, message = "price must be greater than 0")
    private double price;

    @Column(name = "payment_method")
    @NotBlank(message = "payment method is required")
    private String paymentMethod;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentTime;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}