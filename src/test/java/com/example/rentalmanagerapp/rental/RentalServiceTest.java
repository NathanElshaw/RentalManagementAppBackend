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

import java.util.Optional;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

        Rental testRental = rentalArgumentCaptor.getValue();

        assertThat(testRental).isNotNull();
        assertThat(testRental.getRentalAddress()).isEqualTo(address);
    }

    @Test
    void createRentalWillThrow(){

        when(rentalRepository
                .assertRentalByAddress(address))
                .thenReturn(true);

        assertThatThrownBy(()->underTest.createRental(rental))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Address already exists");
    }

    @Test
    void updateRental() {

        when(rentalRepository.findById(rental.getId()))
                .thenReturn(Optional.of(rental));

        underTest.updateRental(rental);

        ArgumentCaptor<Rental>  rentalArgumentCaptor =
                ArgumentCaptor.forClass(Rental.class);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(rentalRepository).updateRental(
                longArgumentCaptor.capture(),
                rentalArgumentCaptor.capture()
        );

        Rental testRental = rentalArgumentCaptor.getValue();

        assertThat(testRental).isNotNull();
        assertThat(testRental.getRentalAddress()).isEqualTo(address);
    }

    @Test
    void updateRentalWillThrow(){
        assertThatThrownBy(()->underTest.updateRental(rental))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No Rentals Exist");
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