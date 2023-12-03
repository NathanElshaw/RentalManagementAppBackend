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
    private int unitNumber;
    private int beds;
    private double baths;
    private String unitAddress;
    private boolean hasPets;
    private double rentAmount;
    private String rentDueDate;
    private String leaseStart;
    private String leaseEnd;

    @AllArgsConstructor
    public static class GetRentalRequest{
        private User user;
        private String unitCode;
    }
}
