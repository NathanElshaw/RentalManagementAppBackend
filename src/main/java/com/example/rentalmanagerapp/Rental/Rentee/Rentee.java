package com.example.rentalmanagerapp.Rental.Rentee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="RENTEES")
public class Rentee {
    @SequenceGenerator(
            name = "renteeSequence",
            sequenceName = "renteeSequence"
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "renteeSequence"
    )
    @Id
    private Long id;
    private String tenantName;
    private String leaseTerm;
    private LocalDate leaseStart;
    private LocalDate leaseEnd;
    private int paymentInterval;
    private boolean hasPaid;
    private RiskFactor riskFactor;

    public Rentee() {

    }
}
