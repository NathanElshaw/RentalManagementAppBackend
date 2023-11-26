package com.example.rentalmanagerapp.Rental;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RentalRequest {
    private String rentalAddress;
    private String description;
    private String type;
    private double rentAmount;
    private String dateAvailable;
}
