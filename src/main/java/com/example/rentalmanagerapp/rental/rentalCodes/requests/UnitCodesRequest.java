package com.example.rentalmanagerapp.rental.rentalCodes.requests;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UnitCodesRequest {
    private final long expiresIn;

    private final String parentUnitAddress;

    private final int parentUnitNumber;

}
