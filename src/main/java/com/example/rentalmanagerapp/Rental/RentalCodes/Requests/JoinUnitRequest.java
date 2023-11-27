package com.example.rentalmanagerapp.Rental.RentalCodes;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class JoinUnitRequest{
    private String unitCode;
    private Long userId;
}
