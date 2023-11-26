package com.example.rentalmanagerapp.Rental;

import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodeRepository;
import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodeService;
import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UnitCodeService unitCodeService;

    public String createRental(RentalRequest rentalRequest){
        boolean addressExists = rentalRepository.findByRentalAddress(rentalRequest.getRentalAddress()).isPresent();

        if(addressExists){
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

    public String joinRental(String unitCode) {
        UnitCodes unitCodeData = unitCodeService.findByCode(unitCode).orElseThrow(() ->
                new IllegalStateException("Invalid Code")
        );

        LocalDateTime expiresAt = unitCodeData.getExpiresAt();

        if(expiresAt == null){
            throw new IllegalStateException("Unit has Already been confirmed");
        }

        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token has expired");
        }

        unitCodeService.setConfirmedAt(unitCode);
        //Todo add rentee id to rental and rental unit to user

    return "";
    }
}
