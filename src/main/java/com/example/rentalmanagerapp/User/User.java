package com.example.rentalmanagerapp.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @SequenceGenerator(
            name = "userSequence",
            sequenceName = "userSequence"
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
    private Boolean isActive;
    private Boolean isLocked;
    private UserRoles userRole;
    private Boolean hasUnit;

    //Rental info
    private LocalDate rentDue;
    private LocalDate rentLastPaid;
    private LocalDate dateLeaseStarted;
    private Long amountPaid;
    private Long amountOwed;
    private String rentalAddress;


    public User() {

    }
}
