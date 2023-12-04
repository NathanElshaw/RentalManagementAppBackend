package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.units.Units;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="rental_codes")
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

    public UnitCodes(
            Long id,
            String unitCode,
            LocalDateTime confirmedAt,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt) {
        this.id = id;
        this.unitCode = unitCode;
        this.confirmedAt = confirmedAt;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public UnitCodes(
            String unitCode,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            Units parentUnitId) {
        this.unitCode = unitCode;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.parentRental = parentUnitId;
    }
}
