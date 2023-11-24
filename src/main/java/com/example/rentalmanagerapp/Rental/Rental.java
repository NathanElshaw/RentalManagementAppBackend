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
    @ManyToOne
    private Rentee renter = null;
    @ManyToMany
    private List<Units> subUnits = null;
    private int totalTenants = 0;
    private long rentAmount;
    private long totalRentIncome = 0L;
    private LocalDate dateAvailable;


    public Rental() {

    }

    public Rental(String rentalAddress,
                  String description,
                  long rentAmount,
                  LocalDate dateAvailable) {
        this.rentalAddress = rentalAddress;
        this.description = description;
        this.rentAmount = rentAmount;
        this.dateAvailable = dateAvailable;
    }

    @OneToOne
    @JoinColumn(name="Renter_id", nullable = false)
    public Rentee getRenter() {
        return renter;
    }
}
