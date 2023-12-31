package com.example.rentalmanagerapp.registration;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RegistrationRequest {
    private final String firstName;

    private final String lastName;

    private final String address;

    private final LocalDate dateOfBirth;

    private final String username;

    private final String password;

    private final String email;

    private final String telephone;
}
