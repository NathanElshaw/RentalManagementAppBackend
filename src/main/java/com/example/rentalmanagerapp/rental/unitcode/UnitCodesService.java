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
            UnitCodes unitCodes){

        boolean parentUnitExist = unitsRepository
                .assertUnitExistsById(unitCodes
                        .getParentRental().getId());

        if(!parentUnitExist){
            throw error("unit does not exist");
        }

        unitCodes.setIssuedAt(LocalDateTime.now());
        unitCodes.setExpiresAt(LocalDateTime.now().plusHours(24));

        repository.save(unitCodes);

        //may need to then get from repo for correct id assignment for next func

        unitsRepository.addUnitCodeToRental(
                unitCodes,
                unitCodes.getParentRental()
                        .getUnitAddress(),
                unitCodes.getParentRental()
                        .getUnitNumber());

        return "Success";
    }


    public String joinUnit(
            JoinUnitRequest joinUnitPayload){
        UnitCodes targetUnitCode = repository.findByUnitCode(
                joinUnitPayload.getUnitCode())
                .orElseThrow(this::codeNotFound);

        Units targetUnit = unitsRepository
                .findById(targetUnitCode.getParentRental().getId())
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
        UnitCodes updateUnitCode = repository
                .findById(updateCodePayload.getUnitCodeId())
                .orElseThrow(this::codeNotFound);

        updateUnitCode.setUnitCode(
                UUID.randomUUID().toString());

        updateUnitCode.setIssuedAt(
                LocalDateTime.now());

        updateUnitCode.setExpiresAt(
                LocalDateTime.now().plusHours(
                        updateCodePayload.getValidLength()));

        repository.update(
                updateCodePayload.getUnitCodeId(),
                updateUnitCode
        );

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

}
