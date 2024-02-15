package com.example.rentalmanagerapp.rental.units;

import java.time.LocalDate;

public record UnitsDTO(

        Long id,
         String unitAddress,

         int beds,

         double bath,

         int unitNumber,

         Boolean hasPets,

         double rentAmount,

         double rentDue,

         double rentPaid,

         LocalDate leaseStart,

         LocalDate rentDueDate,

         LocalDate leaseEnd
) {
}
