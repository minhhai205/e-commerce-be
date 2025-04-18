package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_sku_id")
    private ProductSku productSku;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}