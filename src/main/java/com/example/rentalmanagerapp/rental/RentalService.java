package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.rental.rentalCodes.UnitCodesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                LocalDate.parse(rentalRequest.getDateAvailable())
        );
        rentalRepository.save(newRental);
        return "";
    }

    public List<Rental> getAllRentals(){
        List<Rental> returnRentalList = new ArrayList<>();
        List<Rental> rentals = rentalRepository.getAllUnits().orElseThrow(
                ()->new IllegalStateException("No Rentals Exist")
                );

         rentals.forEach(listRental -> {
            Rental returnRental = new Rental(
                    listRental.getId(),
                    listRental.getRentalAddress(),
                    listRental.getDescription(),
                    listRental.getType(),
                    listRental.getTotalTenants(),
                    listRental.getTotalUnits(),
                    listRental.getAvgRentAmount(),
                    listRental.getTotalRentIncome(),
                    listRental.getDateAvailable()
            );
            returnRentalList.add(returnRental);
        });

        return returnRentalList;
    }

}
