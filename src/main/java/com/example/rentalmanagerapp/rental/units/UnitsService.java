package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.AdminRentalDTOMapper;
import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodeRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserDTOMapper;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UnitsService {

    private final UnitsRepository repository;

    private final UserRepository userRepository;

    private final RentalRepository rentalRepository;

    private final UnitCodeRepository unitCodeRepository;

    private final AdminRentalDTOMapper rentalDTOMapper;

    private final UserDTOMapper userDTOMapper;

    private final UnitsDTOMapper unitsDTOMapper;

    private IllegalStateException unitNotFound(){
        return new IllegalStateException("Unit not found");
    }

    private IllegalStateException throwUnitsError(String s){
        return new IllegalStateException(s);
    }

    public String createUnit(
             Units unit){
        boolean doesExist =
                repository.assertUnitExistByAddressAndNumber(
                        unit.getUnitAddress(),
                        unit.getUnitNumber()
                );

        if(doesExist){
            throw throwUnitsError("Unit already exists");
        }

        Rental parentRental =
                    rentalRepository.findByRentalAddress(
                                    unit.getUnitAddress())
                            .orElseThrow(() ->
                                    throwUnitsError("Parent Rental doesnt exist"));

        if(unit.getParentUnitId() == null) {
            unit.setParentUnitId(parentRental);
        }

        rentalRepository.updateRentalOnNewUnit(
                unit.getUnitAddress(),
                (
                        (parentRental.getAvgRentAmount() *
                                parentRental.getTotalUnits()) +
                                unit.getRentAmount()) /
                        (parentRental.getTotalUnits() + 1),
                parentRental.getTotalRentIncome() +
                              unit.getRentAmount(),
                parentRental.getTotalUnits() + 1);

        repository.save(unit);

        return "Success";
    }

    public List<UnitsDTO> getAllUnitsByAddress(
            String payloadAddress){
        List<UnitsDTO> returnUnitsList = new ArrayList<>();

        List<Units> returnedUnits =
                repository.getAllUnitByAddress(
                payloadAddress);

        if(returnedUnits.isEmpty()){
            throw throwUnitsError("No units found");
        }

            returnedUnits.forEach(rental -> {
                returnUnitsList.add(
                        unitsDTOMapper.apply(rental)
                );
            });

        return returnUnitsList;
    }

    public UnitsDTO getRentalWithCode(
            String code){
        Units targetUnit = unitCodeRepository
                .findByUnitCode(code)
                .orElseThrow(()->
                        throwUnitsError("Code is invalid"))
                .getParentRental();

        return unitsDTOMapper.apply(targetUnit);
    }

    public String updateUnit(
            Units unitPayload){

        repository.findById(
                unitPayload.getId()
        ).orElseThrow(this::unitNotFound);

        unitPayload.setUpdatedAt(LocalDateTime.now());

        repository.save(unitPayload);

        return "Successful Update";
    }

    public UnitsDTO userIdGetUnits (
            Principal user){

        User reqUser = (User)
                ((UsernamePasswordAuthenticationToken) user)
                        .getPrincipal();

        boolean userExist = userRepository
                .assertUserExists(reqUser.getId());

        if(!userExist){
            throw throwUnitsError("User not found");
        }

        Units targetUnit = repository
                .getUnitByUserId(reqUser)
                .orElseThrow(
                ()->throwUnitsError(
                        "User is not apart of any units"));

        if(!targetUnit.isShareInfo() &&
                !reqUser.getIsPrimeRenter()){
            targetUnit.setRenterAmount(0);
            targetUnit.setRentAmount(0);
            targetUnit.setRentPaid(0);
        }

        return unitsDTOMapper.apply(targetUnit);
    }

    public List<Units.GetAllUnitsWithDetails> getAllUnitsWithDetails (){
        List<Units.GetAllUnitsWithDetails> returnedData = new ArrayList<>();

        List<Units> returnedUnitsList = repository.getAllUnits();

        if(returnedUnitsList.isEmpty()){
            throw throwUnitsError("No units Exist");
        }

        returnedUnitsList.forEach(unit -> {
            Units.GetAllUnitsWithDetails addingUnit = new Units.GetAllUnitsWithDetails(
                    unit.getId(),
                    rentalDTOMapper.apply(unit
                            .getParentUnitId()),
                    unit.getUnitCode() == null ?
                            null :
                            unit.getUnitCode(),
                    unit.getPrimRenter() == null ?
                            null :
                            userDTOMapper.apply(unit
                                    .getPrimRenter()),
                    unit.getUnitAddress(),
                    unit.getBeds(),
                    unit.getBaths(),
                    unit.getUnitNumber(),
                    unit.getHasPets(),
                    unit.getRentAmount(),
                    unit.getRentDue(),
                    unit.getRentPaid(),
                    unit.getLeaseStart(),
                    unit.getRentDueDate(),
                    unit.getLeaseEnd());

            returnedData.add(addingUnit);

        });

        return returnedData;

    }

    public String deleteUnit(Units unit){
        Rental parentRental = rentalRepository
                .findByRentalAddress(unit.getUnitAddress())
                .orElseThrow(()->throwUnitsError("Rental not found"));

        parentRental.setAvgRentAmount(
                ((parentRental.getAvgRentAmount() * parentRental.getTotalUnits()) -
                        unit.getRentAmount()) / parentRental.getTotalUnits() - 1
        );

        parentRental.setTotalUnits(
                parentRental.getTotalUnits() - 1
        );

        parentRental.setTotalTenants(
                parentRental.getTotalTenants() - unit.getRenterAmount()
        );

        rentalRepository.save(parentRental);

        repository.delete(unit);

        return "Success";
    }

}
