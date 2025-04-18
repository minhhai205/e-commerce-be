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

    private String name;

    private String shortDesc;

    @Column(columnDefinition = "TEXT")
    private String longDesc;

    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "product")
//    private Set<Review> reviews;

//    @OneToMany(mappedBy = "product")
//    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private Set<ProductSku> productSku;

}