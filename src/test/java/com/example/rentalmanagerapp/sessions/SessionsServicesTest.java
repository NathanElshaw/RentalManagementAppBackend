package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionsServicesTest {

    @Mock
    private SessionsRepository sessionsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionsServices underTest;

    private Sessions sessions;

    private User user;

    @BeforeEach
    void spinUp(){
        underTest = new SessionsServices(
                sessionsRepository,
                userRepository
        );

        user = new User(
                name,
                email,
                username
        );

        sessions =  new Sessions(
                user,
                1,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void createSession() {

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        underTest.createSession(user);

        ArgumentCaptor<Sessions>  sessionsArgumentCaptor =
                ArgumentCaptor.forClass(Sessions.class);

        verify(sessionsRepository)
                .save(sessionsArgumentCaptor.capture());

        Sessions testSession = sessionsArgumentCaptor.getValue();

        assertThat(testSession).isNotNull();
        assertThat(testSession.getSessionAmount()).isEqualTo(1);
        assertThat(testSession.getUser()).isEqualTo(user);
    }

    @Test
    void createSessionWillThrow(){
        assertThatThrownBy(()-> underTest
                .createSession(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not found");
    }

    @Test
    void createSessionWillUpdate(){
        when(sessionsRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(sessions));

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        underTest.createSession(user);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Sessions> sessionsArgumentCaptor =
                ArgumentCaptor.forClass(Sessions.class);

        verify(sessionsRepository).updateUserSession(
                longArgumentCaptor.capture(),
                sessionsArgumentCaptor.capture()
        );
    }

    @Test
    void updateSession() {
        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        when(sessionsRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(sessions));

        underTest.updateSession(user);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Sessions> sessionsArgumentCaptor =
                ArgumentCaptor.forClass(Sessions.class);

        verify(sessionsRepository).updateUserSession(
                longArgumentCaptor.capture(),
                sessionsArgumentCaptor.capture()
        );

        Sessions testSession = sessionsArgumentCaptor.getValue();

        assertThat(testSession).isNotNull();
        assertThat(testSession.getSessionAmount()).isEqualTo(2);
        assertThat(testSession.getUser()).isEqualTo(user);
    }

    @Test
    void updateSessionWillThrowNoUser() {
        assertThatThrownBy(()->
                underTest.updateSession(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not found");
    }

    @Test
    void updateSessionWillThrowNoSession(){
        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        assertThatThrownBy(() ->
                underTest.updateSession(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Session not found");
    }

    @Test
    void deleteSession() {

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        underTest.deleteSession(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        ArgumentCaptor<Boolean> booleanArgumentCaptor =
                ArgumentCaptor.forClass(Boolean.class);

        verify(sessionsRepository).changeSessionStatus(
                userArgumentCaptor.capture(),
                booleanArgumentCaptor.capture()
        );
    }

    @Test
    void deleteSessionWillThrow(){
        assertThatThrownBy(()->
                underTest.deleteSession(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("User not found");
    }
}