package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static com.example.rentalmanagerapp.rental.issues.enums.IssuePriority.Low;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class IssuesServicesTest {

    @Mock
    private IssuesRepository issuesRepository;

    @Autowired
    private UnitsRepository unitsRepository;

    @Autowired
    private RentalRepository rentalRepository;

    private AutoCloseable autoCloseable;

    @Mock
    private UserRepository userRepository;

    private IssuesServices underTest;

    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new IssuesServices(issuesRepository, userRepository);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void canGetAll() {

        underTest.getAllIssues();

        verify(issuesRepository).findAll();

    }

    @Test
    void canCheckForIssues() {

        User user = userRepository.findByEmail(email)
                        .orElseThrow(()-> new IllegalStateException(""));

        underTest.checkForIssues(user);

        ArgumentCaptor<Issues> issuesArgumentCaptor =
                ArgumentCaptor.forClass(Issues.class);
    }

    @Test
    void canCreateIssue() {
        Issues issue = new Issues(
                "Issue",
                Low,
                32
        );

        underTest.createIssue(issue);

        ArgumentCaptor<Issues> issuesArgumentCaptor = ArgumentCaptor.forClass(Issues.class);



    }

    @Test
    void getRentalIssues() {
    }

    @Test
    void updateSeenBy() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void deleteIssue() {
    }

    public boolean makeEqual(String[] words) {
        int amount = 0;
        for (String word : words) {
            amount = amount + word.length();
            System.out.println(amount);
        }
        if(amount == words.length) {
            return false;
        }else {
            return amount % words.length == 0;
        }
    }
}
}