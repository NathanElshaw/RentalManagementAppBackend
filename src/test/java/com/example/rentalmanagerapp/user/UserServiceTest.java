package com.example.rentalmanagerapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService underTest;

    private User user;

    private User.UserLoginRequest login = new User.UserLoginRequest(
            username,
            "Password"
    );


    @BeforeEach
    void spinUp(){
        underTest = new UserService(
                userRepository,
                encoder
        );

        user = new User(
                name,
                email,
                username
        );
    }

    @Test
    void createUser() {

        underTest.createUser(user, "");

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User testUser = userArgumentCaptor.getValue();

        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo(email);
        assertThat(testUser.getUsername()).isEqualTo(username);
    }

    @Test
    void createUserWillThrowEmail(){
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(()-> underTest.createUser(user, ""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Email already exists");
    }

    @Test
    void createUserWillThrowUsername(){
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(()-> underTest.createUser(user, ""))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User already exists");
    }

    @Test
    void updateUser() {

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        underTest.updateUser(user);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).updateUser(
                longArgumentCaptor.capture(),
                userArgumentCaptor.capture()
        );
    }

    @Test
    void updateUserWillThrow(){
        assertThatThrownBy(()-> underTest.updateUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot update");
    }

    @Test
    void deleteUser(){

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        underTest.deleteUser(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .delete(userArgumentCaptor.capture());
    }

    @Test
    void deleteUserWillThrow(){
        assertThatThrownBy(()-> underTest.deleteUser(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User does not exist");
    }

    @Test
    void userLogin() {

        String encodedPassword = encoder.encode("password");

        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        when(encoder.matches(
                login.getPassword(),
                user.getPassword()
        )).thenReturn(true);


        underTest.userLogin(login);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(userRepository)
                .findByUsername(stringArgumentCaptor.capture());

        verify(encoder)
                .matches(
                        stringArgumentCaptor.capture(),
                        stringArgumentCaptor.capture()
                );

    }

    @Test
    void userLoginWillThrowUsername(){

        assertThatThrownBy(()-> underTest.userLogin(login))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid username or password");
    }

    @Test
    void userLoginWillThrowPassword(){

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));


        assertThatThrownBy(()->
                underTest.userLogin(login))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid username or password");
    }
}