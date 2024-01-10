package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodeRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitsServiceTest {

    @Mock
    private UnitsRepository unitsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UnitCodeRepository unitCodeRepository;

    @InjectMocks
    private UnitsService underTest;

    private Units unit;

    private Rental rental;

    private User user;

    private final String code = UUID.randomUUID().toString();

    @BeforeEach
     void spinUp(){
        underTest = new UnitsService(
                unitsRepository,
                userRepository,
                rentalRepository,
                unitCodeRepository
        );

        rental = new Rental(
            address,
            rentalType
        );

        user = new User(
                name,
                email,
                address
        );

        unit = new Units(
                address,
                unitNumber,
                rental
        );

    }

    @Test
    void canCreateUnit() {

        when(unitsRepository
                .assertUnitExistByAddressAndNumber(
                        unit.getUnitAddress(),
                        unit.getUnitNumber()
                )).thenReturn(false);

        when(rentalRepository
                .findByRentalAddress(address))
                .thenReturn(Optional.of(rental));

        underTest.createUnit(unit);

        ArgumentCaptor<Units> unitsArgumentCaptor =
                ArgumentCaptor.forClass(Units.class);

        verify(unitsRepository)
                .save(unitsArgumentCaptor.capture());

        Units testUnit = unitsArgumentCaptor.getValue();

        assertThat(testUnit).isNotNull();
        assertThat(testUnit.getUnitAddress()).isEqualTo(address);
    }

    @Test
    void createUnitWillThrowUnitExists(){
        when(unitsRepository
                .assertUnitExistByAddressAndNumber(
                        unit.getUnitAddress(),
                        unit.getUnitNumber()
                )).thenReturn(true);

        assertThatThrownBy(()->underTest.createUnit(unit))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unit already exists");
    }

    @Test
    void createUnitWillThrowNoParent(){
        when(unitsRepository
                .assertUnitExistByAddressAndNumber(
                        unit.getUnitAddress(),
                        unit.getUnitNumber()
                )).thenReturn(false);

        assertThatThrownBy(()->underTest.createUnit(unit))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Parent Rental doesnt exist");
    }

    @Test
    void getAllUnitsByAddress() {

        List<Units> unitsList = new ArrayList<>();

        unitsList.add(unit);

        when(unitsRepository
                .getAllUnitByAddress(address))
                .thenReturn(unitsList);

        underTest.getAllUnitsByAddress(address);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(unitsRepository)
                .getAllUnitByAddress(stringArgumentCaptor.capture());

        List<Units> testUnits = underTest.getAllUnitsByAddress(address);

        assertThat(testUnits).isNotNull();
        assertThat(testUnits.size()).isEqualTo(1);
        assertThat(testUnits.get(0).getUnitNumber()).isEqualTo(unitNumber);
    }

    @Test
    void getAllUnitsByAddressWillThrow(){
        assertThatThrownBy(() -> underTest
                .getAllUnitsByAddress(address))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No units found");
    }

    @Test
    void getRentalWithCode() {

        UnitCodes unitCode = new UnitCodes(
                code,
                unit
        );

        when(unitCodeRepository.findByUnitCode(code))
                .thenReturn(Optional.of(unitCode));

        underTest.getRentalWithCode(code);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(unitCodeRepository)
                .findByUnitCode(stringArgumentCaptor.capture());

        Units testUnits = underTest.getRentalWithCode(code);

        assertThat(testUnits).isNotNull();
        assertThat(testUnits.getUnitNumber()).isEqualTo(unitNumber);
        assertThat(testUnits.getUnitAddress()).isEqualTo(address);
    }

    @Test
    void getRentalWithCodeWillThrow(){

        assertThatThrownBy(()-> underTest
                .getRentalWithCode(code))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Code is invalid");
    }

    @Test
    void updateUnit() {
        when(unitsRepository.findById(unit.getId()))
                .thenReturn(Optional.of(unit));

        underTest.updateUnit(unit);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Units> unitsArgumentCaptor =
                ArgumentCaptor.forClass(Units.class);

        verify(unitsRepository).updateUnit(
                longArgumentCaptor.capture(),
                unitsArgumentCaptor.capture()
        );

        Units testUnits = unitsArgumentCaptor.getValue();

        assertThat(testUnits).isNotNull();
        assertThat(testUnits.getUnitNumber()).isEqualTo(unitNumber);
    }

    @Test
    void canUserIdGetUnits() {

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        when(unitsRepository
                .getUnitByUserId(user))
                .thenReturn(Optional.of(unit));

        underTest.userIdGetUnits(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(unitsRepository)
                .getUnitByUserId(userArgumentCaptor.capture());

        Units.ReturnGetUnitsRequest testUnits =
                underTest.userIdGetUnits(user);

        assertThat(testUnits).isNotNull();
        assertThat(testUnits.getUnitNumber()).isEqualTo(unitNumber);
        assertThat(testUnits.getHasPets()).isNull();
    }

    @Test
    void userIdGetUnitWillThrowNoUser(){

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(false);

        assertThatThrownBy(()->underTest
                .userIdGetUnits(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not found");
    }

    @Test
    void userIdGetUnitWillThrowNoUnit(){

        when(userRepository.assertUserExists(user.getId()))
                .thenReturn(true);

        assertThatThrownBy(() -> underTest
                .userIdGetUnits(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User is not apart of any units");
    }

    @Test
    void getAllUnitsWithDetails() {

        List<Units> unitsList = new ArrayList<>();

        unitsList.add(unit);

        when(unitsRepository.getAllUnits())
                .thenReturn(unitsList);

        underTest.getAllUnitsWithDetails();

        List<Units.GetAllUnitsWithDetails> testUnits =
                underTest.getAllUnitsWithDetails();

        assertThat(testUnits).isNotNull();
        assertThat(testUnits.size()).isEqualTo(1);
        assertThat(testUnits.get(0).getUnitNumber()).isEqualTo(unitNumber);
    }

    @Test
    void getAllUnitsWithDetailsWillThrow(){
        assertThatThrownBy(() -> underTest
                .getAllUnitsWithDetails())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No units Exist");
    }
}