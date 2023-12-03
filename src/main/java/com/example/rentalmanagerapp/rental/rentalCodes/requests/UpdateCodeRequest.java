package com.example.rentalmanagerapp.rental.rentalCodes.requests;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UpdateCodeRequest {
    private final Long unitCodeId;

    private final Long validLength;
}
