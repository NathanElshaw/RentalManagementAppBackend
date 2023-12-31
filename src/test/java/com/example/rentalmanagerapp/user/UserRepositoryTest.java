package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    void addUnitToUser() {
    }
}