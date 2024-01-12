package com.example.rentalmanagerapp.rental;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.rentalmanagerapp.globals.GlobalVars.address;
import static com.example.rentalmanagerapp.globals.GlobalVars.rentalType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository underTest;

    @BeforeEach
    void spinUp(){
        underTest.save(
                new Rental(
                        address,
                        rentalType
                )
        );
    }

    @AfterEach
    void spinDown(){
        underTest.deleteAll();
    }

    @Test
    void findByRentalAddress() {
        Rental testRental = underTest
                .findByRentalAddress(address)
                .orElseThrow(()-> new IllegalStateException("A"));

        assertThat(testRental).isNotNull();
        assertThat(testRental.getRentalAddress()).isEqualTo(address);
    }

    @Test
    void getRentalByAssignedManager() {
        //Do later
    }

    @Test
    void getAllUnits() {
        List<Rental> testRental = underTest
                .getAllUnits();

        assertThat(testRental).isNotNull();
        assertThat(testRental.size()).isEqualTo(1);
        assertThat(testRental.get(0).getRentalAddress()).isEqualTo(address);
    }

    @Test
    @Disabled
    void updateRentalOnNewUnit() {
    }

    @Test
    @Disabled
    void updateRentalIncome() {
    }

    @Test
    @Disabled
    void updateRental() {
    }

    @Test
    @Disabled
    void addManagerToRental() {
    }

    @Test
    @Disabled
    void addManagerToComplex() {
    }
}