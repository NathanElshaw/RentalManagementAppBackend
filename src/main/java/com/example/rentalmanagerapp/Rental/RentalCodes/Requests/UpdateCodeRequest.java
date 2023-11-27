package com.example.rentalmanagerapp.Rental.RentalCodes.Requests;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UpdateCodeRequest {
    private Long unitCodeId;
    private Long validLength;
}
