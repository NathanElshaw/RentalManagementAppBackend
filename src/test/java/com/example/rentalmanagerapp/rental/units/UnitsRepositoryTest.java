package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodeRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitsRepositoryTest {

    @Autowired
    private UnitsRepository underTest;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UnitCodeRepository unitCodeRepository;

    @Autowired
    private UserRepository userRepository;

    private Rental rental;

    private User user;

    private Units parentUnit;

    private UnitCodes unitCode;

    @BeforeEach
    void spinUp(){

         rentalRepository.save(new Rental(
                 address,
                 rentalType
         ));

        userRepository.save(new User(
                name,
                email,
                username
        ));

        rental = rentalRepository
                .findByRentalAddress(address)
                .orElseThrow(()->new IllegalStateException("A"));

        user = userRepository
                .findByEmail(email)
                        .orElseThrow(()-> new IllegalStateException("B"));

        underTest.save(
                new Units(
                        user,
                        address,
                        unitNumber,
                        rental
                )
        );

        parentUnit = underTest
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber
                ).orElseThrow(()->new IllegalStateException("C"));

        unitCodeRepository.save(
                new UnitCodes(
                        UUID.randomUUID().toString(),
                        parentUnit
                )
        );

        unitCode = unitCodeRepository
                .findById(1L)
                .orElseThrow(()->new IllegalStateException("D"));
    }

    @AfterEach
    void spinDown(){
        rentalRepository.deleteAll();
        userRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void getUnitByUserId() {

        Units testUnit = underTest
                .getUnitByUserId(user)
                .orElseThrow(()->new IllegalStateException("User not found"));

        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getId()).isEqualTo(1L);
        assertThat(testUnit.getUnitNumber()).isEqualTo(unitNumber);

    }

    @Test
    void assertUnitExistsById(){

        boolean testUnit = underTest.assertUnitExistsById(1L);

        assertThat(testUnit).isEqualTo(true);
    }

    @Test
    void findByAddressAndUnitNumber() {

        Units testUnit = underTest
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber
                )
                .orElseThrow(()->new IllegalStateException("Unit not found"));

        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getId()).isEqualTo(1L);
        assertThat(testUnit.getUnitNumber()).isEqualTo(unitNumber);
    }

    @Test
    void findByUser() {

        Units testUnit = underTest
                .findByUser(user)
                .orElseThrow(()-> new IllegalStateException("Unit not found"));

        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getId()).isEqualTo(1L);
        assertThat(testUnit.getUnitNumber()).isEqualTo(unitNumber);
    }

    @Test
    void addUnitCodeToRental() {

        assertThat(unitCode).isNotNull();

        underTest.addUnitCodeToRental(
                unitCode,
                address,
                unitNumber
        );

        Units testUnit = underTest
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber
                )
                .orElseThrow(()->new IllegalStateException("Unit not found"));

        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getId()).isEqualTo(1L);
        assertThat(testUnit.getUnitNumber()).isEqualTo(unitNumber);
        assertThat(testUnit.getUnitCode()).isNotNull();
    }

    @Test
    void addRenterToUnit() {
    }

    @Test
    void findByUnitAddressAndUnitNumber() {
    }

    @Test
    void updateUnit() {
    }

    @Test
    void userPayment() {
    }

    @Test
    void getAllUnitByAddress() {
    }

    @Test
    void getAllUnits() {
    }

  }