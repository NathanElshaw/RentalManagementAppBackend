package com.example.rentalmanagerapp.rental.rentee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="rentee")
public class Rentee {
    @SequenceGenerator(
            name = "renteeSequence",
            sequenceName = "renteeSequence",
            allocationSize = 1
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
