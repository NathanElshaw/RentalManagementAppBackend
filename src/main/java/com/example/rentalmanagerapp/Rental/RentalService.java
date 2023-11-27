package com.example.rentalmanagerapp.Rental;

import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodesService;
import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UnitCodesService unitCodeService;

    public String createRental(RentalRequest rentalRequest) {
        boolean addressExists = rentalRepository.findByRentalAddress(rentalRequest.getRentalAddress()).isPresent();

        if (addressExists) {
            throw new IllegalStateException("Address already exists");
        }

        Rental newRental = new Rental(
                rentalRequest.getRentalAddress(),
                rentalRequest.getDescription(),
                rentalRequest.getType(),
                rentalRequest.getRentAmount(),
                LocalDate.parse(rentalRequest.getDateAvailable())
        );
        rentalRepository.save(newRental);
        return "";
    }

}
