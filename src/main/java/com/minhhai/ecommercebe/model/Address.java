package com.minhhai.ecommercebe.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends AbstractEntity<Long> {

    @Column(columnDefinition = "TEXT")
    private String detail;

    private String district;

    private String province;

    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
