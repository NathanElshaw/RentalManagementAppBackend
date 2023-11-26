package com.example.rentalmanagerapp.Rental.Units;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UnitsRequest {
    private int unitNumber;
    private int beds;
    private double baths;
    private String unitAddress;
    private boolean hasPets;
    private double rentAmount;
    private String rentDueDate;
    private String leaseStart;
    private String leaseEnd;
}
