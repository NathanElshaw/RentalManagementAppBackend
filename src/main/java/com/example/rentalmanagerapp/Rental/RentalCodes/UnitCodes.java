package com.example.rentalmanagerapp.Rental.RentalCodes;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.Units.Units;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Getter
@Setter
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
    private Units parentRental;
    private LocalDateTime confirmedAt;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    public UnitCodes(){

    }

    public UnitCodes(String unitCode, LocalDateTime issuedAt, LocalDateTime expiresAt, Units parentUnitId) {
        this.unitCode = unitCode;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.parentRental = parentUnitId;
    }
}
