package com.minhhai.ecommercebe.model;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "orders")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AbstractEntity<Long> {
    @NotBlank(message = "Recipient address cannot be blank!")
    private String receiverAddress;

    @NotBlank(message = "Recipient phone number cannot be blank!")
    private String receiverPhoneNumber;

    @Column(nullable = false)
    @DecimalMin(value = "0", inclusive = false, message = "Total price in order must be greater than 0!")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "Order status", regexp = "PENDING|CONFIRMED|SHIPPED|DELIVERED|CANCELLED")
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

}