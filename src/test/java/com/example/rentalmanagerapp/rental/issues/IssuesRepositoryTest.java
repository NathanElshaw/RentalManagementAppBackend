package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static com.example.rentalmanagerapp.rental.issues.enums.IssuePriority.Low;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class IssuesRepositoryTest {

    @Autowired
    private IssuesRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitsRepository unitsRepository;

    @Autowired
    private RentalRepository rentalRepository;

    void spinUp(){
        userRepository.save(
                new User(
                        name,
                        email,
                        username
                )
        );

        rentalRepository.save(
                new Rental(
                        address,
                        rentalType
                )
        );

        Rental parentRental = rentalRepository
                .findByRentalAddress(address)
                .orElseThrow(()-> new IllegalStateException(""));

        assertThat(parentRental).isNotNull();
        assertThat(parentRental.getId()).isEqualTo(1L);

        unitsRepository.save(
                new Units(
                        address,
                        unitNumber,
                        parentRental
                )
        );

    }

    @Test
    void checkForIssues() {

        spinUp();

        User testUser = userRepository
                .findByEmail(email)
                    .orElseThrow(()-> new IllegalStateException(""));

        Units testUnit = unitsRepository
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber
                ).orElseThrow(()-> new IllegalStateException(""));

        Issues issues = new Issues(
                testUser,
                testUnit,
                testUnit.getUnitNumber(),
                Low,
                "Issue Body test",
                address
        );

        underTest.save(issues);

        List<Issues> testIssue = underTest
                .checkForIssue(testUser);

        assertThat(testUser).isNotNull();
        assertThat(testUser.getId()).isEqualTo(1L);
        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getUnitNumber()).isNotNull();
        assertThat(testIssue).isNotNull();
        assertThat(testIssue.size()).isEqualTo(1);
        assertThat(testIssue.get(0).getId()).isEqualTo(1L);

    }

    @Test
    void getRentalsIssuesByAddress() {

        spinUp();

        User testUser = userRepository
                .findByEmail(email)
                .orElseThrow(()-> new IllegalStateException(""));

        Units testUnit = unitsRepository
                .findByAddressAndUnitNumber(
                        address,
                        unitNumber
                ).orElseThrow(()-> new IllegalStateException(""));

        Issues issues = new Issues(
                testUser,
                testUnit,
                testUnit.getUnitNumber(),
                Low,
                "Issue Body test",
                address
        );

        underTest.save(issues);

        List<Issues> testIssues = underTest
                .getRentalsIssuesByAddress(address);

        assertThat(testIssues).isNotNull();
        assertThat(testIssues.size()).isEqualTo(1);
        assertThat(testIssues.get(0).getId()).isEqualTo(1L);
        assertThat(testIssues.get(0).getIssueBody()).isEqualTo("Issue Body test");

    }

    @Test
    @Disabled
    void updateStatus()  {

    }

    @Test
    @Disabled
    void updateSeenBy() {
    }
}