package com.example.rentalmanagerapp.rental;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RentalRequest {
    private final  String rentalAddress;

    private final String description;

    private final String type;

    private final String dateAvailable;
}
