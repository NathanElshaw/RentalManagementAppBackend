package com.example.rentalmanagerapp.rental.rentalCodes.requests;

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
