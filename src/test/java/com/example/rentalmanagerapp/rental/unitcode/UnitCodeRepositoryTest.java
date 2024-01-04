package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.user.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    void spinUp(){

        Rental parentRental = rentalRepository
                .findByRentalAddress(address)
                .orElseThrow(()-> new IllegalStateException(""));

        assertThat(parentRental).isNotNull();
        assertThat(parentRental.getId()).isEqualTo(1L);
    }

    @Test
    void findByUnitCode() {
        rentalRepository.save(
                new Rental(
                        address,
                        rentalType
                )
        );

        Rental testRental = rentalRepository
                .findById(1L)
                .orElseThrow(()->new IllegalStateException(""));

        assertThat(testRental).isNotNull();
        assertThat(testRental.getId()).isEqualTo(1L);
        assertThat(testRental.getRentalAddress()).isEqualTo(address);
        assertThat(testRental.getType()).isEqualTo(rentalType);
    }

    @Test
    @Disabled
    void updateConfirmedAt() {
    }

    @Test
    void getUnitWithCode() {
    }
}