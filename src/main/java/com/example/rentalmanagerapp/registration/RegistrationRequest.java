package com.example.rentalmanagerapp.Registration;

import lombok.*;

import java.time.LocalDate;

/*
* Template for registration requests for creating an account*/
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RegistrationRequest {
    //Basic info
    private final String firstName;
    private final String lastName;
    private final String address;
    private final LocalDate dateOfBirth;

    //Credentials info
    private final String username;
    private final String password;

    //Contact info
    private final String email;
    private final String telephone;
}
