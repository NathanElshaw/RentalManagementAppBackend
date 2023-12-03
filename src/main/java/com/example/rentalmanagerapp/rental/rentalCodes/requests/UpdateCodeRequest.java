package com.example.rentalmanagerapp.rental.rentalCodes.requests;

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
