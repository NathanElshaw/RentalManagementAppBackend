package com.example.rentalmanagerapp.Rental.Rentee;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Entity
@Table(name="Renters", schema="")
public class Rentee {
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
