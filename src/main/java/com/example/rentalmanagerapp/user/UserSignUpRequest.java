package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UserSignUpRequest {
    private final String firstName;

    private final String lastName;

    private final String birthDate;

    private final String address;

    private final String email;

    private final String telephone;

    private final String username;

    private final String password;

}
