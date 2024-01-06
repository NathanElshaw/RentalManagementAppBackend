package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.example.rentalmanagerapp.globals.GlobalVars.address;
import static com.example.rentalmanagerapp.globals.GlobalVars.email;
import static com.example.rentalmanagerapp.globals.GlobalVars.name;
import static com.example.rentalmanagerapp.globals.GlobalVars.rentalType;
import static com.example.rentalmanagerapp.globals.GlobalVars.unitNumber;
import static com.example.rentalmanagerapp.globals.GlobalVars.username;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UnitCodesServiceTest {

    @Mock
    private  UnitCodeRepository unitCodeRepository;

    @Mock
    private UnitsRepository unitsRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable autoCloseable;

    private UnitCodesService underTest;

    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UnitCodesService(
                unitCodeRepository,
                unitsRepository,
                userRepository
        );
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void createUnitCode() {

        String code = UUID.randomUUID().toString();

        Rental rental = new Rental(
                address,
                rentalType
        );

        Units unit =  new Units(
                address,
                unitNumber,
                rental
        );

        UnitCodes testCode = new UnitCodes(
                code,
                unit
        );

        given(unitsRepository
                .assertUnitExistsById(unit.
                        getId())).willReturn(true);

        underTest.createUnitCode(testCode);

        ArgumentCaptor<UnitCodes> unitCodesArgumentCaptor = ArgumentCaptor
                .forClass(UnitCodes.class);

        verify(unitCodeRepository).save(unitCodesArgumentCaptor.capture());

        UnitCodes codesCaptor = unitCodesArgumentCaptor.getValue();

        assertThat(codesCaptor).isNotNull();
        assertThat(codesCaptor.getUnitCode()).isEqualTo(code);
        assertThat(codesCaptor.getConfirmedAt()).isNull();
    }

    @Test
    void joinUnit() {

        String code = UUID.randomUUID().toString();

        User user = new User(
                name,
                email,
                username
        );

        Rental rental = new Rental(
                address,
                rentalType
        );

        Units unit =  new Units(
                address,
                unitNumber,
                rental
        );


        UnitCodes testCode = new UnitCodes(
                code,
                unit
        );

        unitCodeRepository.save(testCode);


        given(unitCodeRepository
                .findByUnitCode(code)).willReturn(Optional.of(testCode));

        given(unitsRepository
                .findById(testCode
                        .getParentRental()
                        .getId()))
                .willReturn(
                        Optional.of(unit));

        given(userRepository
                .findById(
                        unit.getId()))
                .willReturn(Optional.of(user));

        underTest.createUnitCode(testCode);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Integer> intArgumentCaptor =
                ArgumentCaptor.forClass(Integer.class);

        ArgumentCaptor<LocalDateTime> dateTimeArgumentCaptor =
                ArgumentCaptor.forClass(LocalDateTime.class);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        ArgumentCaptor<UnitCodes> unitCodesArgumentCaptor =
                ArgumentCaptor.forClass(UnitCodes.class);

        ArgumentCaptor<Units> unitsArgumentCaptor =
                ArgumentCaptor.forClass(Units.class);

        verify(userRepository)
                .findById(longArgumentCaptor.capture());

        verify(unitsRepository)
                .findById(longArgumentCaptor.capture());

        verify(unitCodeRepository)
                .findByUnitCode(stringArgumentCaptor.capture());

        verify(unitCodeRepository)
                .updateConfirmedAt(
                        stringArgumentCaptor.capture(),
                        dateTimeArgumentCaptor.capture()
                );

        verify(userRepository)
                .addUnitToUser(
                        userArgumentCaptor.capture(),
                        unitsArgumentCaptor.capture()
                );

        verify(unitsRepository)
                .addUnitCodeToRental(
                        unitCodesArgumentCaptor.capture(),
                        stringArgumentCaptor.capture(),
                        intArgumentCaptor.capture()
                );
    }

    @Test
    void updateCode() {
    }

    @Test
    void deleteUnitCode() {
    }
}