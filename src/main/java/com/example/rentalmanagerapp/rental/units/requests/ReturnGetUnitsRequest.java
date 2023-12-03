package com.example.rentalmanagerapp.rental.units.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ReturnGetUnitsRequest {
    private String unitAddress;
    private int beds;
    private double baths;
    private int unitNumber;
    private Boolean hasPets;
    private double rentAmount;
    private double rentDue;
    private double rentPaid;
    private LocalDate leaseStart;
    private LocalDate rentDueDate;
    private LocalDate leaseEnd;

}
