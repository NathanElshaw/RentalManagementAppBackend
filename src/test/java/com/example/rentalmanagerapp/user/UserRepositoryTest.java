package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void findByEmail() {
        String email = "Test1@Test.com";
        User user = new User(
                "Test1",
                email
        );
        underTest.save(user);

        User testUser = underTest
                .findByEmail(email)
                .orElseThrow(()->new IllegalStateException(""));

        assertThat(testUser).isNotNull();
    }

    @Test
    void findByUsername() {
    }

    @Test
    void findByUsernameLogin() {
    }

    @Test
    void addUnitToUser() {
    }
}