package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail extends AbstractEntity<Long> {

    @Min(value = 1, message = "Quantity must be at least 1!")
    @Column(name = "quantity")
    private long quantity;

    @Column(nullable = false)
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0!")
    private BigDecimal priceEach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id")
    private ProductSku productSku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}