package com.example.rentalmanagerapp.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;


    private final String name = "test";

    private final String email = "Test@Test.com";

    private final String username = "TestingUser";

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
    }
}