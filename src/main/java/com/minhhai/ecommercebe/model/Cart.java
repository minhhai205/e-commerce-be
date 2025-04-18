package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends AbstractEntity<Integer> {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartDetail> cartDetails;
}