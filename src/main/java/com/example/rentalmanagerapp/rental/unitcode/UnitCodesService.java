package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.unitcode.requests.JoinUnitRequest;
import com.example.rentalmanagerapp.rental.unitcode.requests.UnitCodesRequest;
import com.example.rentalmanagerapp.rental.unitcode.requests.UpdateCodeRequest;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.IllformedLocaleException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UnitCodesService {

    private final UnitCodeRepository repository;

    private final UnitsRepository unitsRepository;

    private final UserRepository userRepository;

    private IllegalStateException codeNotFound(){
        return new IllegalStateException("Code is invalid");
    }

    private IllegalStateException error(String s){
        return new IllegalStateException(s);
    }

    public String createUnitCode(
            UnitCodesRequest unitCodesPayload){
        Units parentUnit = unitsRepository.findByUnitAddressAndUnitNumber(
                unitCodesPayload.getParentUnitNumber(),
                unitCodesPayload.getParentUnitAddress()).orElseThrow(
                        ()-> error("Parent unit not found"));
        if(parentUnit.getUnitCode() != null){
            throw error("Invalid address or unit number");
        }

        UnitCodes newUnitCodePayload = new UnitCodes(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(
                        unitCodesPayload.getExpiresIn()
                ),
                parentUnit);

        repository.save(newUnitCodePayload);

        unitsRepository.addUnitCodeToRental(
                newUnitCodePayload,
                unitCodesPayload.getParentUnitAddress(),
                unitCodesPayload.getParentUnitNumber());

        return "Success";
    }


    public String joinUnit(
            JoinUnitRequest joinUnitPayload){
        UnitCodes targetUnitCode = repository.findByUnitCode(
                joinUnitPayload.getUnitCode())
                .orElseThrow(this::codeNotFound);

        Units targetUnit = unitsRepository
                .findById(targetUnitCode.getId())
                .orElseThrow(
                ()->error( "Unit is invalid"));

        User targetUser = userRepository
                .findById(joinUnitPayload.getUserId())
                .orElseThrow(
                ()->error("User not found"));

        repository.updateConfirmedAt(
                joinUnitPayload.getUnitCode(), LocalDateTime.now());

        userRepository.addUnitToUser(
                targetUser, targetUnit
        );

        unitsRepository.addRenterToUnit(
                targetUser, targetUnit.getId(), targetUnit.getRentAmount());

        return "Successfully added user " +
                targetUser.getFirstName() +
                " " +
                targetUser.getLastName() +
                " To unit";
    }

    public String updateCode(
            UpdateCodeRequest updateCodePayload){
        UnitCodes targetUnitCode = repository
                .findById(updateCodePayload.getUnitCodeId())
                .orElseThrow(this::codeNotFound);

        targetUnitCode.setUnitCode(
                UUID.randomUUID().toString());

        targetUnitCode.setIssuedAt(
                LocalDateTime.now());

        targetUnitCode.setExpiresAt(
                LocalDateTime.now().plusHours(
                        updateCodePayload.getValidLength()));

        repository.save(targetUnitCode);

        return "Successfully Reissued";
    }

    public String deleteUnitCode(Long unitCodeId){
        UnitCodes targetUnitCode = repository
                .findById(unitCodeId)
                .orElseThrow(
                ()->error("Unit code not found"));

        repository.delete(targetUnitCode);
        return "Successfully removed unit code";
    }

    public Optional<UnitCodes> findByCode(
            String code){
        return repository.findByUnitCode(code);
    }

}
