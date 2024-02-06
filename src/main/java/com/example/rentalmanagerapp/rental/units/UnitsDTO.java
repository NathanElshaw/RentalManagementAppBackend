package com.example.rentalmanagerapp.rental.units;

public record UnitsDTO(
    long id,

    String address,

    int beds,

    double baths,

    int unitNumber
) {
}
