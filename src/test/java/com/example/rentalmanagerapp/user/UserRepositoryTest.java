package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Autowired
    private UnitsRepository unitsRepository;

    @Autowired
    private RentalRepository rentalRepository;


    private final String name = "test";

    private final String email = "Test@Test.com";

    private final String username = "TestingUser";

    private final String testAddress = "1234 Testing Ln";

    private final  int unitNumber = 34;

    private final String rentalType = "Apartment";

    @Test
    void findByEmail() {
        User user = new User(
                name,
                email,
                username
        );
        underTest.save(user);

        User testUser = underTest
                .findByEmail(email)
                .orElseThrow(()->new IllegalStateException(""));

        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo(email);
        assertThat(testUser.getUsername()).isEqualTo(username);
    }

    @Test
    void findByUsername() {

        User user = new User(
                name,
                email,
                username
        );

        underTest.save(user);

        User testUser = underTest
                .findByEmail(email)
                .orElseThrow(()->new IllegalStateException(""));

        assertThat(testUser).isNotNull();
        assertThat(testUser.getUsername()).isEqualTo(username);
    }


    @Test
    void addUnitToUser() {
        User user = new User(
                name,
                email,
                username
        );
        underTest.save(user);

        Rental rental = new Rental(
                testAddress,
                rentalType
        );
        rentalRepository.save(rental);

        Rental parentRental = rentalRepository
                .findByRentalAddress(testAddress)
                .orElseThrow(()->new IllegalStateException(""));

        Units unit = new Units(
                testAddress,
                unitNumber,
                parentRental
        );
        unitsRepository.save(unit);

        Units testUnits = unitsRepository
                .findByAddressAndUnitNumber(
                        testAddress,
                        unitNumber)
                        .orElseThrow(()->new IllegalStateException(""));


        User dbUser = underTest
                .findByEmail(email)
                .orElseThrow(()->new IllegalStateException(""));

        underTest.addUnitToUser(dbUser, testUnits);

        User testUser = underTest
                .findById(1L)
                .orElseThrow(()->new IllegalStateException(""));

        System.out.println(testUser.getUsersUnit());
        assertThat(testUser).isNotNull();
        assertThat(parentRental).isNotNull();
        assertThat(testUnits).isNotNull();
        assertThat(testUser.getUsersUnit()).isNotNull();
    }
}