package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.model.*;
import com.minhhai.ecommercebe.util.annotations.EnumPattern;
import com.minhhai.ecommercebe.util.enums.Gender;
import com.minhhai.ecommercebe.util.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponseDTO implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String urlAvatar;

    private Gender gender;

    private Status status;

    private Set<AddressResponseDTO> addresses;

}
