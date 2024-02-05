package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class AdminRentalDTO implements Function<Rental, RentalDTO.AdminRentalDTO> {

    private final UserDTOMapper dtoMapper;

    @Override
    public RentalDTO.AdminRentalDTO apply(Rental rental){
        return new RentalDTO.AdminRentalDTO(
                rental.getRentalAddress(),
                rental.getDescription(),
                rental.getType(),
                rental.getTotalUnits(),
                rental.getTotalTenants(),
                rental.getAvgRentAmount(),
                rental.getTotalRentIncome(),
                dtoMapper.apply(rental.getAssignedManager())
        );
    }
}
