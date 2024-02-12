package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.registration.RegistrationService;
import com.example.rentalmanagerapp.rental.unitcode.requests.JoinUnitRequest;import com.example.rentalmanagerapp.rental.unitcode.requests.UpdateCodeRequest;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UnitCodesService {

    private final RegistrationService registrationService;

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
                .assertUnitExistByAddressAndNumber(
                        unitCodes.getParentUnitAddress(),
                        unitCodes.getParentUnitNumber());

        if(!parentUnitExist){
            throw error("Unit does not exist");
        }

        unitCodes.setUnitCode(registrationService.createToken());
        unitCodes.setIssuedAt(LocalDateTime.now());
        unitCodes.setExpiresAt(LocalDateTime.now().plusHours(24));

        repository.save(unitCodes);

        //may need to then get from repo for correct id assignment for next func

        unitsRepository.addUnitCodeToRental(
                unitCodes,
                unitCodes.getParentUnitAddress(),
                unitCodes.getParentUnitNumber());

        return "Success";
    }

    public boolean checkCode(String code){
        return repository.findByUnitCode(code).isPresent();
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



        targetUnit.setUpdatedAt(LocalDateTime.now());
        repository.save(targetUnitCode);

        userRepository.addUnitToUser(
                targetUser, targetUnit
        );

        unitsRepository.addRenterToUnit(
                targetUser, targetUnit.getId(), targetUnit.getRentAmount());

        return "Successfully added user " +
                targetUser.getFullName() +
                " To unit";
    }

    public void createUserJoinUnit(User user, String joinCode){

        UnitCodes unitCode = repository.findByUnitCode(
                        joinCode)
                .orElseThrow(this::codeNotFound);

        Units unit = unitsRepository
                .findByAddressAndUnitNumber(
                        unitCode.getParentUnitAddress(),
                        unitCode.getParentUnitNumber()
                ).orElseThrow(()-> new BadRequestException("Unit not found"));

        user.setUsersUnit(unit);
        user.setRentalAddress(unit.getUnitAddress());
        unit.setRenter(user);

        unitsRepository.save(unit);
        userRepository.save(user);
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
