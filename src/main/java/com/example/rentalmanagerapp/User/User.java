package com.example.rentalmanagerapp.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @SequenceGenerator(
            name = "userSequence",
            sequenceName = "userSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "userSequence"
    )

    //Reference
    @Id
    private Long id;

    //Basic Info
    private String firstName;
    private String lastName;
    private String fullName;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    //Contact Info
    private String address;
    private String email;
    private String telephone;

    //Credentials
    private String username;
    private String password;

    //Authority
    private Boolean isActive = false;
    private Boolean isLocked = false;
    private UserRoles userRole = UserRoles.User;
    private Boolean hasUnit = false;

    //Rental info
    private LocalDate rentDue = null;
    private LocalDate rentLastPaid = null;
    private LocalDate dateLeaseStarted = null;
    private Long amountPaid = 0L;
    private Long amountOwed = 0L;
    private String rentalAddress = null;


    public User() {

    }

    public User(String firstName, String lastName, String fullName, LocalDate birthDate, String address, String email, String telephone, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
    }

    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class UserLoginRequest{
        private String username;
        private String password;
    }

}
