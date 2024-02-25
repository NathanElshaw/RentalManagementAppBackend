package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.registration.RegistrationService;
import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.unitcode.requests.UpdateCodeRequest;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsDTO;
import com.example.rentalmanagerapp.rental.units.UnitsDTOMapper;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UnitCodesService {

    private final RegistrationService registrationService;

    private final UnitCodeRepository repository;

    private final UnitsRepository unitsRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    private final UnitsDTOMapper unitsDTOMapper;

    private IllegalStateException codeNotFound(){
        return new IllegalStateException("Code is invalid");
    }

    private BadRequestException error(String s){
        return new BadRequestException(s);
    }

    public String createUnitCode(
            UnitCodes unitCodes){

        String code = unitCodes.getUnitCode();
        boolean parentUnitExist = unitsRepository
                .assertUnitExistByAddressAndNumber(
                        unitCodes.getParentUnitAddress(),
                        unitCodes.getParentUnitNumber());

        if(!parentUnitExist){
            throw error("Unit does not exist");
        }

        if(unitCodes.getUnitCode() == null) {
             code = registrationService.createToken();

            if (checkCode(code)) {
                while (checkCode(code)) {
                    code = registrationService.createToken();
                }
            }
        }

        unitCodes.setUnitCode(code);
        unitCodes.setIssuedAt(LocalDateTime.now());
        unitCodes.setExpiresAt(LocalDateTime.now().plusHours(24));

        repository.save(unitCodes);

        unitsRepository.addUnitCodeToRental(
                repository
                        .findByUnitCode(code)
                        .orElseThrow(()->error("Internal Error")),
                unitCodes.getParentUnitAddress(),
                unitCodes.getParentUnitNumber());

        return "Success";
    }

    public boolean checkCode(String code){
        return repository.findByUnitCode(code).isPresent();
    }

    public UnitsDTO getUnitWithCode(String code){
        UnitCodes unitCode = repository.findByUnitCode(
                code)
                .orElseThrow(()-> new BadRequestException("Code not valid"));

        if(unitCode.getExpiresAt().isBefore(LocalDateTime.now())){
            expireCode(unitCode);
            throw new BadRequestException("Code is expired");
        }else{
            Units unit = unitsRepository.findByAddressAndUnitNumber(
                    unitCode.getParentUnitAddress(),
                    unitCode.getParentUnitNumber()
            ).orElseThrow(()->
                    new BadRequestException("Unit not found")
            );

            return unitsDTOMapper.apply(unit);
        }
    }

    public void expireCode(UnitCodes unitCode){
        unitCode.setUnitCode(null);
        unitCode.setExpiresAt(null);
        unitCode.setIssuedAt(null);

        repository.save(unitCode);
    }


    public String joinUnit(
            String code,
            Principal user){

        User reqUser = (User)
                ((UsernamePasswordAuthenticationToken) user)
                        .getPrincipal();

        UnitCodes targetUnitCode = repository.findByUnitCode(
                code)
                .orElseThrow(this::codeNotFound);

        Units unit = unitsRepository
                .findById(targetUnitCode.getParentRental().getId())
                .orElseThrow(
                ()->error( "Unit is invalid"));

        if(targetUnitCode.getExpiresAt().isBefore(LocalDateTime.now())){
            expireCode(targetUnitCode);
            throw new BadRequestException("Code is expired");
        }

        if(unit.getPrimRenter() != null){

            reqUser.setUsersUnit(unit);
            unit.setRenterAmount(unit.getRenterAmount() + 1);

            unitsRepository.save(unit);
            userRepository.save(reqUser);
        }else {

            Rental rental = rentalRepository
                    .findByRentalAddress(unit.getUnitAddress())
                    .orElseThrow(() -> error("Internal error"));

            unit.setUpdatedAt(LocalDateTime.now());
            reqUser.setUsersUnit(unit);
            reqUser.setIsPrimeRenter(true);
            unit.setPrimRenter(reqUser);
            rental.setAvgRentAmount(
                    ((rental.getAvgRentAmount() * rental.getTotalTenants()) + unit.getRentAmount()) /
                            rental.getTotalTenants() + 1
            );

            repository.save(targetUnitCode);
            unitsRepository.save(unit);
            rentalRepository.save(rental);
            userRepository.save(reqUser);

        }
        return "Successfully added user " +
                reqUser.getFullName() +
                " To unit";
    }

    public void createUserJoinUnit(User user, String joinCode){

        UnitCodes unitCode = repository.findByUnitCode(
                        joinCode)
                .orElseThrow(this::codeNotFound);

        if(unitCode.getExpiresAt().isBefore(LocalDateTime.now())){
            expireCode(unitCode);
            throw new BadRequestException("Code is expired");
        }

        user = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow(()-> error("Internal Error"));

        Units unit = unitsRepository
                .findByAddressAndUnitNumber(
                        unitCode.getParentUnitAddress(),
                        unitCode.getParentUnitNumber()
                ).orElseThrow(()-> new BadRequestException("Unit not found"));

        Rental rental = rentalRepository
                .findByRentalAddress(unit.getUnitAddress())
                        .orElseThrow(()-> error("Internal error"));

        user.setUsersUnit(unit);
        user.setRentalAddress(unit.getUnitAddress());
        unit.setPrimRenter(user);
        rental.setTotalTenants(
                rental.getTotalTenants() + 1
        );

        userRepository.save(user);
        unitsRepository.save(unit);
        repository.delete(unitCode);
    }

    public String updateCode(
            UnitCodes unitCode){
        UnitCodes targetUnitCode = repository
                .findById(unitCode.getId())
                .orElseThrow(this::codeNotFound);

        if(targetUnitCode == null){
            throw new BadRequestException("Code not valid");
        }

        if(unitCode.getUnitCode() == null){
            unitCode.setUnitCode(
                    registrationService.createToken());
        }

        unitCode.setIssuedAt(
                LocalDateTime.now());

        unitCode.setExpiresAt(
                LocalDateTime.now().plusHours(24));

        repository.save(unitCode);

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
