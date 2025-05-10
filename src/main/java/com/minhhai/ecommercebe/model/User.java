package com.minhhai.ecommercebe.model;

import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.Gender;
import com.minhhai.ecommercebe.util.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> {
    @NotBlank(message = "First name must not be blank!")
    private String firstName;

    @NotBlank(message = "Last name must not be blank!")
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email must not be blank!")
    @Email(message = "Email is not valid!")
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username must not be blank!")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password must not be blank!")
    private String password;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @NotBlank(message = "Phone number must not be blank!")
    private String phoneNumber;

    private String urlAvatar;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "Gender user", regexp = "MALE|FEMALE")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @EnumPattern(name = "User status", regexp = "ACTIVE|INACTIVE|LOCKED")
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    private boolean deleted = false;

    public void addAddress(Address address) {
        if (address == null) return;

        if (addresses == null) {
            addresses = new HashSet<>();
        }

        addresses.add(address);
        address.setUser(this);
    }
}
