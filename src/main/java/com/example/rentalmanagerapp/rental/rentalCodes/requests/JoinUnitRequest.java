package com.example.rentalmanagerapp.rental.rentalCodes.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class JoinUnitRequest{
    private final  String unitCode;

    private final Long userId;
}
