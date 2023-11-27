package com.example.rentalmanagerapp.Rental.RentalCodes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UnitCodeService {

    private final UnitCodeRepository unitCodeRepository;

    public Optional<UnitCodes> findByCode(String code){
        return unitCodeRepository.findByUnitCode(code);
    }

    public int setConfirmedAt(String token){
        return unitCodeRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
