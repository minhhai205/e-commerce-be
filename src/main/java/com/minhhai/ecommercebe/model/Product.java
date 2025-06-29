package com.minhhai.ecommercebe.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity<Long> {

    @Column(nullable = false)
    @NotBlank(message = "Product name cannot be blank!")
    private String name;

    @NotBlank(message = "Short description must not be blank!")
    private String shortDesc;

    @NotBlank(message = "Long description must not be blank")
    @Column(columnDefinition = "TEXT")
    private String longDesc;

    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductSku> productSku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private boolean deleted = false;

}