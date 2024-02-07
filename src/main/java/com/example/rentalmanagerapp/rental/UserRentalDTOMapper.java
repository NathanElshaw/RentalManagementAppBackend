package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
@Getter
public class UserRentalDTOMapper implements Function<Rental.Dto, RentalDTO> {

    private final UserDTOMapper userDTOMapper;

    @Override
    public RentalDTO apply(Rental.Dto rentalDto){

        Units unit = rentalDto.getUnits();

        Rental rental = rentalDto.getRental();

        return new RentalDTO(
                rental.getRentalAddress(),
                rental.getType(),
                rental.getTotalUnits(),
                userDTOMapper.apply(
                        rental.getAssignedManager()
                ),
                unit.getUnitNumber(),
                unit.getBeds(),
                unit.getBaths(),
                unit.getRentAmount(),
                unit.getLeaseStart(),
                unit.getRentDueDate(),
                unit.getLeaseEnd()
        );
    }
}

