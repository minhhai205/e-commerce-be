package com.minhhai.ecommercebe.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.Gender;
import com.minhhai.ecommercebe.util.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserRequestDTO implements Serializable {
    @NotBlank(message = "First name must not be blank!")
    private String firstName;

    @NotBlank(message = "Last name must not be blank!")
    private String lastName;

    @NotBlank(message = "Email must not be blank!")
    @Email(message = "Email is not valid!")
    private String email;

    @NotBlank(message = "Username must not be blank!")
    private String username;

    @NotBlank(message = "Password must not be blank!")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number must not be blank!")
    private String phoneNumber;

    private String urlAvatar;

    @EnumPattern(name = "Gender user", regexp = "MALE|FEMALE")
    private Gender gender;

    @EnumPattern(name = "User status", regexp = "ACTIVE|INACTIVE|LOCKED")
    private Status status;

    @NotNull(message = "addresses must not be null!")
    private Set<AddressRequestDTO> addresses;

    @NotNull(message = "roleNames must not be null!")
    private List<String> roleNames;
}
