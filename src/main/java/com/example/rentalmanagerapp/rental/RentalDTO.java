package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Getter
public record RentalDTO(
        String address,

        String type,

        int totalUnits,

        UserDTO assignedManager,

        //Unit

        int unitNumber,

        int beds,

        double baths,

        double rentAmount,

        LocalDate leaseStart,

        LocalDate rentDueDate,

        LocalDate leaseEnd
) {
    @Getter
    @AllArgsConstructor
    public record AdminRentalDTO(
            String address,

            String description,

            String type,

            int totalUnits,

            int totalTenants,

            double avgRentAmount,

            double totalRentIncome,

            UserDTO assignedManager
    ){

    }
}
