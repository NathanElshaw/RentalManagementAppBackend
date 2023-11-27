package com.example.rentalmanagerapp.Rental.RentalCodes.Requests;

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
