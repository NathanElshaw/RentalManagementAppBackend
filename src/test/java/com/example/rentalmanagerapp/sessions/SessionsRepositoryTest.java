package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SessionsRepositoryTest {

    @Autowired
    private SessionsRepository underTest;

    @Autowired
    private UserRepository userRepository;

    private Sessions sessions;

    @BeforeEach
    void spinUp(){
        userRepository.save(new User(
                name,
                email,
                username
        ));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("A"));

        underTest.save( new Sessions(
                user,
                1,
                LocalDateTime.now(),
                LocalDateTime.now()
        ));

        sessions = underTest.findByUserId(user.getId())
                .orElseThrow(()-> new IllegalStateException("B"));

    }

    @AfterEach
    void spinDown(){
        userRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void findByUserId() {
        assertThat(sessions).isNotNull();
        assertThat(sessions.getSessionAmount()).isEqualTo(1);
    }

    @Test
    @Disabled
    void updateUserSession() {
    }
}