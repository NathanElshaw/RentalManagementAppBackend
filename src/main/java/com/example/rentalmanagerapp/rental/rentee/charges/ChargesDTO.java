package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.user.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ChargesDTO (
        UserDTO user,

        String reason,

        UserDTO createdBy,

        boolean hasPaid,

        double amountOwed,

        double amountPaid,

        LocalDateTime paidAt,

        LocalDate dueBy,

        String chargeId,

        String transactionId
){
}
