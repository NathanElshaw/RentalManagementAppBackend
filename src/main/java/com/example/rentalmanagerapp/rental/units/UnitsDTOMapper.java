package com.example.rentalmanagerapp.rental.units;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UnitsDTOMapper implements Function<Units, UnitsDTO> {

    @Override
    public UnitsDTO apply(Units unit){
        return new UnitsDTO(
                unit.getId(),
                unit.getUnitAddress(),
                unit.getBeds(),
                unit.getBaths(),
                unit.getUnitNumber(),
                unit.getHasPets(),
                unit.getRenterAmount(),
                unit.getRentAmount(),
                unit.getRentDue(),
                unit.getRentPaid(),
                unit.getLeaseStart(),
                unit.getRentDueDate(),
                unit.getLeaseEnd()
        );
    }


}
