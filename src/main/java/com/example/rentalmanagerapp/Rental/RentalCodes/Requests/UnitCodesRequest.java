package com.example.rentalmanagerapp.Rental.RentalCodes.Requests;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UnitCodesRequest {
    private long expiresIn;
    private String parentUnitAddress;
    private int parentUnitNumber;

}
