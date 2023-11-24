package com.example.rentalmanagerapp.Rental;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    public String createRental(RentalRequest rentalRequest){
        Rental newRental = new Rental(
                rentalRequest.getRentalAddress(),
                rentalRequest.getDescription(),
                rentalRequest.getRentAmount(),
                LocalDate.parse(rentalRequest.getDateAvailable())
        );
        rentalRepository.save(newRental);
        return "";
    }
}
