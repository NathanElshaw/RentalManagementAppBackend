package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static com.example.rentalmanagerapp.globals.GlobalVars.address;
import static com.example.rentalmanagerapp.globals.GlobalVars.rentalType;
import static com.example.rentalmanagerapp.globals.GlobalVars.unitNumber;
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


    }

    @Test
    void updateCode() {
    }

    @Test
    void deleteUnitCode() {
    }
}