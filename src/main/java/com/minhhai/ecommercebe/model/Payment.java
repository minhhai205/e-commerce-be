package com.minhhai.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

//    @NotBlank(message = "payment method is required")
//    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @EnumPattern(name = "Payment status", regexp = "UNPAID|PAID")
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentTime;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}