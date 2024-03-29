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

    private String parentUnitAddress;

    private int parentUnitNumber;

    @OneToOne
    private Units parentRental;

    private LocalDateTime confirmedAt = null;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public UnitCodes(){

    }

    public UnitCodes(String unitCode, Units parentRental) {
        this.unitCode = unitCode;
        this.parentRental = parentRental;
    }

   public UnitCodes(Long id,
                    String unitCode,
                    Units parentRental) {
        this.id = id;
        this.unitCode = unitCode;
        this.parentRental = parentRental;
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
}
