package com.minhhai.ecommercebe.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Detailed address cannot be left blank!")
    private String detail;

    @NotBlank(message = "District cannot be left blank!")
    private String district;

    @NotBlank(message = "Province can be left blank!")
    private String province;

    @NotBlank(message = "Country can be left blank!")
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
