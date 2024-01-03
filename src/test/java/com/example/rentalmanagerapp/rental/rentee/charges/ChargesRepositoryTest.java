package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.globals.GlobalVars;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ChargesRepositoryTest {

    @Autowired
    private ChargesRepository underTest;

    @Autowired
    private UserRepository userRepository;

    private final String chargeId = "TestCharge1";

    @Test
    void canFindByChargeId() {

        User user = new User(
                name,
                email,
                username
        );

        userRepository.save(user);

        Charges charge = new Charges(
                user,
                "Rent",
                user,
                false,
                100D,
                0D,
                100D,
                chargeId
        );

        underTest.save(charge);

        Charges testCharge = underTest
                .findByChargeId(chargeId)
                .orElseThrow(()->new IllegalStateException("not found"));

        assertThat(testCharge).isNotNull();
        assertThat(testCharge.getChargeId()).isEqualTo(chargeId);
        assertThat(testCharge.getReason()).isEqualTo("Rent");
        assertThat(testCharge.getChargeAmount()).isEqualTo(100D);
        assertThat(testCharge.getAmountOwed()).isEqualTo(100D);
        assertThat(testCharge.getAmountPaid()).isEqualTo(0D);
    }

    @Test
    void canAssertChargeExist(){

        User user = new User(
                name,
                email,
                username
        );

        userRepository.save(user);

        Charges charge = new Charges(
                user,
                "Rent",
                user,
                false,
                100D,
                0D,
                100D,
                chargeId
        );

        underTest.save(charge);

        boolean chargeTest = underTest
                .assertChargeExists(charge);

        assertThat(chargeTest).isEqualTo(true);

    }

    @Test
    @Disabled
    void makePayment() {
    }
}