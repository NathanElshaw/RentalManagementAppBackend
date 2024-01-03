package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.rental.issues.IssuesRepository;
import com.example.rentalmanagerapp.rental.issues.IssuesServices;
import com.example.rentalmanagerapp.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ChargesServiceTest {

    @Mock
    private ChargesRepository chargesRepository;

    private AutoCloseable autoCloseable;

    private ChargesService underTest;

    private final String chargeId = "TestCharge1";

    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ChargesService(chargesRepository);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void canCreateCharge() {

        User user = new User(
                name,
                email,
                username
        );

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

        underTest.createCharge(charge);

        ArgumentCaptor<Charges> chargesArgumentCaptor = ArgumentCaptor
                .forClass(Charges.class);

        verify(chargesRepository)
                .save(chargesArgumentCaptor.capture());

        Charges chargesCaptor = chargesArgumentCaptor.getValue();

        assertThat(chargesCaptor).isNotNull();
        assertThat(chargesCaptor.getChargeId()).isEqualTo(chargeId);
    }

    @Test
    void canUpdateCharge() {

        User user = new User(
                name,
                email,
                username
        );

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

        chargesRepository.save(charge);

        given(chargesRepository.assertChargeExists(charge)).willReturn(true);

        underTest.updateCharge(charge);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor
                .forClass(Long.class);

        ArgumentCaptor<Charges> chargesArgumentCaptor = ArgumentCaptor
                .forClass(Charges.class);

        verify(chargesRepository)
                .updateCharge(
                        longArgumentCaptor.capture(),
                        chargesArgumentCaptor.capture());

        Charges chargesCaptor = chargesArgumentCaptor.getValue();

        assertThat(chargesCaptor).isNotNull();
        assertThat(chargesCaptor.getChargeId()).isEqualTo(chargeId);
    }

    @Test
    void updateChargeWillThrow(){

        User user = new User(
                name,
                email,
                username
        );

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

        given(chargesRepository.assertChargeExists(charge)).willReturn(false);

        assertThatThrownBy(()->underTest.updateCharge(charge))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Charge not found");

    }

    @Test
    void canDeleteCharge() {
        User user = new User(
                name,
                email,
                username
        );

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

        chargesRepository.save(charge);

        given(chargesRepository.assertChargeExists(charge)).willReturn(true);

        underTest.deleteCharge(charge);

        ArgumentCaptor<Charges> chargesArgumentCaptor = ArgumentCaptor
                .forClass(Charges.class);

        verify(chargesRepository)
                .delete(
                        chargesArgumentCaptor.capture());

        assertThat(underTest.deleteCharge(charge)).isEqualTo("Successfully Deleted");

    }

    @Test
    void deleteChargeWillThrow(){
        User user = new User(
                name,
                email,
                username
        );

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

        given(chargesRepository.assertChargeExists(charge)).willReturn(false);

        assertThatThrownBy(()->underTest.deleteCharge(charge))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Charge not found");

    }
}