package com.minhhai.ecommercebe.dto.response;

import com.minhhai.ecommercebe.util.enums.Gender;
import com.minhhai.ecommercebe.util.enums.Status;
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
    private Long id;

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

    private Set<String> roleNames;

}
