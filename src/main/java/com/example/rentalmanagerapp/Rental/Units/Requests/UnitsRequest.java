package com.example.rentalmanagerapp.Rental.Units.Requests;

import com.example.rentalmanagerapp.User.User;
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

    public static class GetRentalRequest{
        private User user;
        private String unitCode;
    }
}
