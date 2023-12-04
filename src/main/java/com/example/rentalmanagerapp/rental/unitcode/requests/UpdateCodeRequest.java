package com.example.rentalmanagerapp.rental.unitcode.requests;

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
public class UpdateCodeRequest {
    private final Long unitCodeId;

    private final Long validLength;
}
