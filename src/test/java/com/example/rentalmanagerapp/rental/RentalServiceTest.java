package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RentalService underTest;

    private Rental rental;

    private User user;

    @BeforeEach
    void spinUp(){
        underTest = new RentalService(
            rentalRepository,
            userRepository
        );

        user = new User(
                name,
                email,
                username
        );

        rental = new Rental(
                address,
                rentalType
        );
    }

    @Test
    void createRental() {

        when(rentalRepository
                .assertRentalByAddress(address))
                .thenReturn(false);

        underTest.createRental(rental);

        ArgumentCaptor<Rental>  rentalArgumentCaptor =
                ArgumentCaptor.forClass(Rental.class);

        verify(rentalRepository)
                .save(rentalArgumentCaptor.capture());
    }

    @Test
    void updateRental() {
    }

    @Test
    void deleteRental() {
    }

    @Test
    void getAllRentals() {
    }

    @Test
    void getPropertyMangerRentals() {
    }
}