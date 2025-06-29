package com.minhhai.ecommercebe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Category name cannot be blank!")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private boolean deleted = false;
}
