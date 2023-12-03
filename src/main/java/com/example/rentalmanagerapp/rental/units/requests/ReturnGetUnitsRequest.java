package com.example.rentalmanagerapp.rental.units.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ReturnGetUnitsRequest {
    private final String unitAddress;

    private final int beds;

    private final double baths;

    private final int unitNumber;

    private final Boolean hasPets;

    private final double rentAmount;

    private final double rentDue;

    private final double rentPaid;

    private final LocalDate leaseStart;

    private final LocalDate rentDueDate;

    private final LocalDate leaseEnd;

}
