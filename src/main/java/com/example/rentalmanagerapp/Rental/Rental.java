package com.example.rentalmanagerapp.Rental;

import com.example.rentalmanagerapp.Rental.Rentee.Rentee;
import com.example.rentalmanagerapp.Rental.Units.Units;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="RENTALS")
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
    private double rentAmount;
    private long totalRentIncome = 0L;
    private LocalDate dateAvailable;


    public Rental() {

    }

    public Rental(String rentalAddress,
                  String description,
                  String type,
                  double rentAmount,
                  LocalDate dateAvailable) {
        this.rentalAddress = rentalAddress;
        this.description = description;
        this.type = type;
        this.rentAmount = rentAmount;
        this.dateAvailable = dateAvailable;
    }
}
