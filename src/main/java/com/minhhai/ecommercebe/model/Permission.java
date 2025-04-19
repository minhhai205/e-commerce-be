package com.minhhai.ecommercebe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class Permission extends AbstractEntity<Integer> {

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Permission's name must be not blank!")
    private String name;

    private String description;
}
