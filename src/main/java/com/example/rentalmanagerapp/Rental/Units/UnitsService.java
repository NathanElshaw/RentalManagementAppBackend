package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UnitsService {

    private final UnitsRepository unitsRepository;
    private final RentalRepository rentalRepository;

    public String createUnit(UnitsRequest createUnitPayload){
        boolean doesExist = unitsRepository.findByUnitAddressAndUnitNumber(createUnitPayload.getUnitNumber(),
                createUnitPayload.getUnitAddress()).isPresent();

        if(doesExist){
            throw new IllegalStateException("Unit Already Exists");
        }

        Rental parentRental = rentalRepository.findByRentalAddress(createUnitPayload.getUnitAddress()).orElseThrow(()->
                new IllegalStateException("Parent Rental doesnt exist"));

        Units newUnit = new Units(
                createUnitPayload.getUnitNumber(),
                createUnitPayload.getBeds(),
                createUnitPayload.getBaths(),
                createUnitPayload.getUnitAddress(),
                createUnitPayload.isHasPets(),
                createUnitPayload.getRentAmount(),
                createUnitPayload.getRentDueDate(),
                createUnitPayload.getLeaseStart(),
                createUnitPayload.getLeaseEnd(),
                parentRental
        );

        unitsRepository.save(newUnit);

        return "Success";
    }

}
