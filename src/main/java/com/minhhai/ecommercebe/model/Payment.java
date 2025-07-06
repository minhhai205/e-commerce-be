package com.minhhai.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.PaymentMethod;
import com.minhhai.ecommercebe.util.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractEntity<Long> {
    @Column(nullable = false)
    @DecimalMin(value = "0", inclusive = false, message = "Total price in order must be greater than 0!")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "Payment method", regexp = "COD|VNPAY")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "Payment status", regexp = "UNPAID|PAID")
    private PaymentStatus paymentStatus;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentTime;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}