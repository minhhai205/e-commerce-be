package com.minhhai.ecommercebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity<Integer> {
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    Set<Product> products;
}
