package com.example.rentalmanagerapp.rental.units.requests;

import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UnitsRequest {
    private final int unitNumber;

    private final int beds;

    private final double baths;

    private final String unitAddress;

    private final boolean hasPets;

    private final double rentAmount;

    private final String rentDueDate;

    private final String leaseStart;

    private final String leaseEnd;

    @AllArgsConstructor
    public static class GetRentalRequest{
        private final User user;

        private final  String unitCode;
    }
}
