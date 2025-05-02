package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products_skus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSku extends AbstractEntity<Long> {
    @NotBlank(message = "Size must not be blank")
    private String size;

    @NotBlank(message = "Color must not be blank")
    private String color;

    @Column(nullable = false)
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0!")
    private BigDecimal priceEach;

    @Column(nullable = false)
    @Min(value = 0, message = "Quantity must be greater than or equal to 0!")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean deleted = false;
}