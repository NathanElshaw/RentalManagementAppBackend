package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.unitcode.requests.UnitCodesRequest;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.example.rentalmanagerapp.globals.GlobalVars.address;
import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static com.example.rentalmanagerapp.globals.GlobalVars.rentalType;
import static com.example.rentalmanagerapp.globals.GlobalVars.unitNumber;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitCodeRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UnitsRepository unitsRepository;

    @Autowired
    private UnitCodeRepository unitCodeRepository;

    private Units spinUp(){
        Rental rental = new Rental(
                address,
                rentalType
        );

        rentalRepository.save(rental);

        Rental parentRental = rentalRepository
                .findByRentalAddress(address)
                .orElseThrow(()->new IllegalStateException("A"));

        Units unit =  new Units(
                address,
                unitNumber,
                parentRental
        );

        unitsRepository.save(unit);

        return unitsRepository
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber)
                .orElseThrow(()->new IllegalStateException(""));


    }

    @Test
    void findByUnitCode() {
        String code = UUID.randomUUID().toString();

        Units parentUnit = spinUp();

        UnitCodes unitCodes = new UnitCodes(
                code,
                parentUnit
        );

        unitCodeRepository.save(unitCodes);

        UnitCodes testUnitCode = unitCodeRepository
                .findByUnitCode(unitCodes.getUnitCode())
                .orElseThrow(()->new IllegalStateException(""));

        assertThat(testUnitCode).isNotNull();
        assertThat(testUnitCode.getId()).isEqualTo(1L);
        assertThat(testUnitCode.getParentRental().getUnitAddress()).isEqualTo(address);
        assertThat(testUnitCode.getUnitCode()).isEqualTo(code);
    }

    @Test
    @Disabled
    void updateConfirmedAt() {
    }
}