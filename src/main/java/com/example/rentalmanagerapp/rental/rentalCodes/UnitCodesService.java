package com.example.rentalmanagerapp.rental.rentalCodes;

import com.example.rentalmanagerapp.rental.rentalCodes.requests.JoinUnitRequest;
import com.example.rentalmanagerapp.rental.rentalCodes.requests.UnitCodesRequest;
import com.example.rentalmanagerapp.rental.rentalCodes.requests.UpdateCodeRequest;
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

    private final UnitCodeRepository unitCodeRepository;
    private final UnitsRepository unitsRepository;
    private final UserRepository userRepository;

    public String createUnitCode(UnitCodesRequest unitCodesPayload){
        Units parentUnit = unitsRepository.findByUnitAddressAndUnitNumber(
                unitCodesPayload.getParentUnitNumber(),
                unitCodesPayload.getParentUnitAddress()).orElseThrow(
                        ()-> new IllegalStateException("Parent unit not found"));

        if(parentUnit.getUnitCode() != null){
            throw new IllegalStateException("Invalid address or unit number");
        }

        UnitCodes newUnitCodePayload = new UnitCodes(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(unitCodesPayload.getExpiresIn()),
                parentUnit
        );

        unitCodeRepository.save(newUnitCodePayload);

        unitsRepository.addUnitCodeToRental(newUnitCodePayload,
                                            unitCodesPayload.getParentUnitAddress(),
                                            unitCodesPayload.getParentUnitNumber());

        return "Success";
    }


    public String joinUnit(JoinUnitRequest joinUnitPayload){
        UnitCodes targetUnitCode = unitCodeRepository.findByUnitCode(joinUnitPayload.getUnitCode()).orElseThrow(
                ()->new IllegalStateException("Code is invalid")
        );
        Units targetUnit = unitsRepository.findById(targetUnitCode.getId()).orElseThrow(
                ()-> new IllegalStateException("Unit is invalid")
        );
        User targetUser = userRepository.findById(joinUnitPayload.getUserId()).orElseThrow(
                ()->new IllegalStateException("User not found")
        );

        unitCodeRepository.updateConfirmedAt(joinUnitPayload.getUnitCode(), LocalDateTime.now());
        unitsRepository.addRenterToUnit(targetUser, targetUnit.getId());

        return "Successfully added user " + targetUser.getFirstName() + " " + targetUser.getLastName() + " To unit";
    }

    public String updateCode(UpdateCodeRequest updateCodePayload){
        UnitCodes targetUnitCode = unitCodeRepository.findById(updateCodePayload.getUnitCodeId()).orElseThrow(
                ()->new IllegalStateException("Code not found")
        );
        targetUnitCode.setUnitCode(UUID.randomUUID().toString());
        targetUnitCode.setIssuedAt(LocalDateTime.now());
        targetUnitCode.setExpiresAt(LocalDateTime.now().plusHours(updateCodePayload.getValidLength()));

        unitCodeRepository.save(targetUnitCode);

        return "Successfully Reissued";
    }

    public String deleteUnitCode(Long unitCodeId){
        UnitCodes targetUnitCode = unitCodeRepository.findById(unitCodeId).orElseThrow(
                ()->new IllformedLocaleException("Unit code doesnt exist")
        );

        unitCodeRepository.delete(targetUnitCode);
        return "Successfully removed unit code";
    }


    //Used to check if that is the unit they want to join before calling join unit
    public Optional<UnitCodes> findByCode(String code){
        return unitCodeRepository.findByUnitCode(code);
    }

}
