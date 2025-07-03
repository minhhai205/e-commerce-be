package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "carts_details")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetail extends AbstractEntity<Long> {

    @Min(value = 1, message = "Quantity must be at least 1!")
    private long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sku_id")
    private ProductSku productSku;
}