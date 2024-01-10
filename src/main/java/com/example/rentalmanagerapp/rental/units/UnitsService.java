package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodeRepository;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.requests.UnitsRequest;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Units> getAllUnitsByAddress(
            String payloadAddress){
        List<Units> returnUnitsList = new ArrayList<>();

        List<Units> returnedUnits =
                repository.getAllUnitByAddress(
                payloadAddress);

        if(returnedUnits.isEmpty()){
            throw throwUnitsError("No units found");
        }

            returnedUnits.forEach(rental -> {
                Units newUnit = new Units(
                        rental.getId(),
                        rental.getParentUnitId(),
                        rental.getUnitCode(),
                        rental.getRenter(),
                        rental.getUnitAddress(),
                        rental.getBeds(),
                        rental.getBaths(),
                        rental.getUnitNumber(),
                        rental.getHasPets(),
                        rental.getRentAmount(),
                        rental.getRentDue(),
                        rental.getRentPaid(),
                        rental.getLeaseStart(),
                        rental.getRentDueDate(),
                        rental.getLeaseEnd(),
                        rental.getCreatedBy(),
                        rental.getCreatedAt(),
                        rental.getUpdatedAt());

                returnUnitsList.add(newUnit);
            });

        return returnUnitsList;
    }

    public Units getRentalWithCode(
            String code){
        return unitCodeRepository
                .findByUnitCode(code)
                .orElseThrow(()->
                        throwUnitsError("Code is invalid"))
                .getParentRental();

        //Todo Validate unitCode then return unit to user to confirm if its the correct one.
    }

    public String updateUnit(
            Units unitPayload){

        repository.findById(
                unitPayload.getId()
        ).orElseThrow(this::unitNotFound);

        unitPayload.setUpdatedAt(LocalDateTime.now());

        repository.updateUnit(
                unitPayload.getId(),
                unitPayload
        );

        return "Successful Update";
    }

    public Units.ReturnGetUnitsRequest userIdGetUnits (
            User user){

        boolean userExist = userRepository
                .assertUserExists(user.getId());

        if(!userExist){
            throw throwUnitsError("User not found");
        }

        Units targetUnit = repository
                .getUnitByUserId(user)
                .orElseThrow(
                ()->throwUnitsError(
                        "User is not apart of any units"));

        return new Units.ReturnGetUnitsRequest(
                targetUnit.getUnitAddress(),
                targetUnit.getBeds(),
                targetUnit.getBaths(),
                targetUnit.getUnitNumber(),
                targetUnit.getHasPets(),
                targetUnit.getRentAmount(),
                targetUnit.getRentDue(),
                targetUnit.getRentPaid(),
                targetUnit.getLeaseStart(),
                targetUnit.getRentDueDate(),
                targetUnit.getLeaseEnd());
    }

    public List<Units.GetAllUnitsWithDetails> getAllUnitsWithDetails (){
        List<Units.GetAllUnitsWithDetails> returnedData = new ArrayList<>();

        List<Units> returnedUnitsList = repository.getAllUnits();

        if(returnedUnitsList.isEmpty()){
            throw throwUnitsError("No units Exist");
        }

        returnedUnitsList.forEach(unit -> {
            Rental parentRental = unit.getParentUnitId();

            UnitCodes unitCode = unit.getUnitCode();

            User renter = unit.getRenter();

            Units.GetAllUnitsWithDetails addingUnit = new Units.GetAllUnitsWithDetails(
                    unit.getId(),
                    new Rental(
                            parentRental.getId(),
                            parentRental.getRentalAddress(),
                            parentRental.getDescription(),
                            parentRental.getType(),
                            parentRental.getTotalTenants(),
                            parentRental.getTotalUnits(),
                            parentRental.getAvgRentAmount(),
                            parentRental.getTotalRentIncome(),
                            parentRental.getAssignedManager(),
                            parentRental.getCreatedBy(),
                            parentRental.getCreatedAt(),
                            parentRental.getUpdatedAt()),
                    unit.getUnitCode() == null ?
                            null :
                            new UnitCodes(
                            unitCode.getId(),
                            unitCode.getUnitCode(),
                            unitCode.getConfirmedAt(),
                            unitCode.getIssuedAt(),
                            unitCode.getExpiresAt()),
                    unit.getRenter() == null ?
                            null :
                            new User.UnitUserRequest(
                            renter.getId(),
                            renter.getFullName(),
                            renter.getBirthDate(),
                            renter.getEmail(),
                            renter.getTelephone(),
                            renter.getUsername(),
                            renter.getUserRole(),
                            renter.getRentDue(),
                            renter.getRentLastPaid(),
                            renter.getDateLeaseStarted(),
                            renter.getAmountPaid(),
                            renter.getAmountOwed()),
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
                    unit.getLeaseEnd()
            );

            returnedData.add(addingUnit);

        });

        return returnedData;

    }

}
