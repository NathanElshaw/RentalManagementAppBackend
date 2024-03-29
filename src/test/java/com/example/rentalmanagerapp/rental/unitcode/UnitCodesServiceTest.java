package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.unitcode.requests.JoinUnitRequest;
import com.example.rentalmanagerapp.rental.unitcode.requests.UpdateCodeRequest;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitCodesServiceTest {

    @Mock
    private  UnitCodeRepository unitCodeRepository;

    @Mock
    private UnitsRepository unitsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UnitCodesService underTest;

    private User user;
    private Units unit;

    private UnitCodes testCode;

    private final String code = UUID.randomUUID().toString();

    @BeforeEach
    void spinUp(){
        underTest = new UnitCodesService(
                unitCodeRepository,
                unitsRepository,
                userRepository
        );
         user = new User(
                 1L,
                name,
                email,
                username
        );

        Rental rental = new Rental(
                address,
                rentalType
        );

         unit =  new Units(
                 user,
                address,
                unitNumber,
                rental
        );

         testCode = new UnitCodes(
                 1L,
                code,
                unit
        );
    }

    @Test
    void canCreateUnitCode() {

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
    void createUnitCodeWillThrow() {
        when(unitsRepository.assertUnitExistsById(1L))
                .thenReturn(false);

        assertThatThrownBy(()->underTest.createUnitCode(testCode))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unit does not exist");
    }

    @Test
    void joinUnit() {

        when(unitCodeRepository
                .findByUnitCode(req.getUnitCode()))
                .thenReturn(Optional.of(testCode));

        when(unitsRepository
                .findById(testCode
                        .getParentRental()
                        .getId()))
                .thenReturn(Optional.of(unit));

        when(userRepository
                .findById(
                        unit.getId()))
                .thenReturn(Optional.of(user));

        underTest.joinUnit(code, (Principal) user);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Double> doubleArgumentCaptor =
                ArgumentCaptor.forClass(Double.class);

        ArgumentCaptor<LocalDateTime> dateTimeArgumentCaptor =
                ArgumentCaptor.forClass(LocalDateTime.class);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

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
                .addRenterToUnit(
                        userArgumentCaptor.capture(),
                        longArgumentCaptor.capture(),
                        doubleArgumentCaptor.capture()
                );
    }

    @Test
    void joinUnitWillThrowCodeInvalid(){
        JoinUnitRequest req = new JoinUnitRequest(
                testCode.getUnitCode(),
                user.getId()
        );

        assertThatThrownBy(
                ()->underTest.joinUnit(
                        code,
                        (Principal) user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Code is invalid");
    }

    @Test
    void joinUnitWillThrowInvalidUnit(){
        JoinUnitRequest req = new JoinUnitRequest(
                testCode.getUnitCode(),
                user.getId()
        );

        when(unitCodeRepository
                .findByUnitCode(req.getUnitCode()))
                .thenReturn(Optional.of(testCode));

        assertThatThrownBy(()-> underTest.joinUnit(
                code,
                (Principal) user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unit is invalid");
    }

    @Test
    void joinUnitWillThrowInvalidUser(){
        JoinUnitRequest req = new JoinUnitRequest(
                testCode.getUnitCode(),
                user.getId()
        );

        when(unitCodeRepository
                .findByUnitCode(req.getUnitCode()))
                .thenReturn(Optional.of(testCode));

        when(unitsRepository
                .findById(testCode.getParentRental().getId()))
                .thenReturn(Optional.of(unit));

        assertThatThrownBy(()->underTest.joinUnit(code,
                (Principal) user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not found");
    }

    @Test
    void canUpdateCode() {

        UpdateCodeRequest req = new UpdateCodeRequest(
                testCode.getId(),
                3L
        );

        when(unitCodeRepository
                .findById(testCode.getId()))
                .thenReturn(Optional.of(testCode));

        underTest.updateCode(req);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor
                        .forClass(Long.class);

        ArgumentCaptor<UnitCodes> unitCodesArgumentCaptor =
                ArgumentCaptor
                        .forClass(UnitCodes.class);

        verify(unitCodeRepository)
                .update(
                        longArgumentCaptor.capture(),
                        unitCodesArgumentCaptor.capture());

        UnitCodes unitCodeCaptor =
                unitCodesArgumentCaptor.getValue();

        assertThat(unitCodeCaptor).isNotNull();
    }

    @Test
    void canDeleteUnitCode() {
        when(unitCodeRepository
                .findById(testCode.getId()))
                .thenReturn(Optional.of(testCode));

        underTest.deleteUnitCode(testCode.getId());

        ArgumentCaptor<UnitCodes> unitCodesArgumentCaptor =
                ArgumentCaptor.forClass(UnitCodes.class);

        verify(unitCodeRepository)
                .delete(unitCodesArgumentCaptor.capture());
    }

    @Test
    void deleteUnitCodeWillThrow() {
        assertThatThrownBy(()->underTest
                .deleteUnitCode(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Unit code not found");
    }
}