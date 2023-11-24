package com.example.rentalmanagerapp.Rental.RentalCodes;

import com.example.rentalmanagerapp.Rental.Rental;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="rentalCodes")
public class UnitCodes {
    @SequenceGenerator(
            name = "rentalCodeSequence",
            sequenceName = "rentalCodeSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "rentalCodeSequence"
    )
    @Id
    private Long id;
    private String unitCode;
    @OneToOne
    private Rental rental_id;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    public UnitCodes(){

    }


}
