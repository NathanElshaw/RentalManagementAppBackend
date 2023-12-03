package com.example.rentalmanagerapp.rental;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="RENTALS")
@Getter
@Setter
public class Rental {
    @SequenceGenerator(
            name = "rentalSequence",
            sequenceName = "rentalSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "rentalSequence"
    )

    @Id
    private Long id;
    private String rentalAddress;
    private String description;
    private String type;
    private int totalTenants = 0;
    private int totalUnits = 0;
    private double avgRentAmount = 0;
    private double totalRentIncome = 0;
    private LocalDate dateAvailable;


    public Rental() {

    }

    public Rental(String rentalAddress,
                  String description,
                  String type,
                  LocalDate dateAvailable) {
        this.rentalAddress = rentalAddress;
        this.description = description;
        this.type = type;
        this.avgRentAmount = avgRentAmount;
        this.dateAvailable = dateAvailable;
    }
}
