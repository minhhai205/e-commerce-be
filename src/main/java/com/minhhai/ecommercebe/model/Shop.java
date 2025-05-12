package com.minhhai.ecommercebe.model;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.ShopStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends AbstractEntity<Integer> {
    @NotBlank(message = "Shop name must not be blank!")
    String name;

    @NotBlank(message = "Shop description must not be blank!")
    String description;

    private String urlAvatar;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "Shop status", regexp = "PENDING|APPROVED|REJECTED|DEACTIVATED|DELETED")
    private ShopStatus shopStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "shop")
    private Set<Order> orders;
}
