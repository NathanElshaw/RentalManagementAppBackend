package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.units.requests.GetUnitRequest;
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

    private IllegalStateException unitNotFound(){
        return new IllegalStateException("Unit not found");
    }

    private IllegalStateException throwUnitsError(String s){
        return new IllegalStateException(s);
    }

    public String createUnit(
            UnitsRequest createUnitPayload){
        boolean doesExist =
                repository.findByUnitAddressAndUnitNumber(
                createUnitPayload.getUnitNumber(),
                createUnitPayload.getUnitAddress()).isPresent();

        if(doesExist){
            throw throwUnitsError("Unit Already Exists");
        }

        Rental parentRental =
                rentalRepository.findByRentalAddress(
                createUnitPayload.getUnitAddress())
                        .orElseThrow(()->
                                throwUnitsError("Parent Rental doesnt exist"));

        Units newUnit = new Units(
                createUnitPayload.getUnitNumber(),
                createUnitPayload.getBeds(),
                createUnitPayload.getBaths(),
                createUnitPayload.getUnitAddress(),
                createUnitPayload.isHasPets(),
                createUnitPayload.getRentAmount(),
                createUnitPayload.getRentDueDate(),
                createUnitPayload.getLeaseStart(),
                createUnitPayload.getLeaseEnd(),
                parentRental
        );

        rentalRepository.updateRentalOnNewUnit(
                createUnitPayload.getUnitAddress(),
                (
                        (parentRental.getAvgRentAmount() *
                                parentRental.getTotalUnits()) +
                                createUnitPayload.getRentAmount()) /
                        (parentRental.getTotalUnits() + 1),
                parentRental.getTotalRentIncome() +
                              createUnitPayload.getRentAmount(),
                parentRental.getTotalUnits() + 1);

        repository.save(newUnit);

        return "Success";
    }

    public List<Units> getAllUnitsByAddress(
            String payloadAddress){
        List<Units> returnUnitsList = new ArrayList<>();

        List<Units> returnedUnits =
                repository.getAllUnitByAddress(
                payloadAddress);

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

    public String getRentalWithCode(
            UnitsRequest.GetRentalRequest addRenterPayload){
        //Todo Validate unitCode then return unit to user to confirm if its the correct one.
        return "";
    }

    public String updateUnit(
            Units unitPayload){

        repository.findById(
                unitPayload.getId()
        ).orElseThrow(this::unitNotFound);

        Units updatedUnit = new Units(
                unitPayload.getId(),
                unitPayload.getParentUnitId(),
                unitPayload.getUnitCode(),
                unitPayload.getRenter(),
                unitPayload.getUnitAddress(),
                unitPayload.getBeds(),
                unitPayload.getBaths(),
                unitPayload.getUnitNumber(),
                unitPayload.getHasPets(),
                unitPayload.getRentAmount(),
                unitPayload.getRentDue(),
                unitPayload.getRentPaid(),
                unitPayload.getLeaseStart(),
                unitPayload.getRentDueDate(),
                unitPayload.getLeaseEnd(),
                unitPayload.getCreatedBy(),
                unitPayload.getCreatedAt(),
                LocalDateTime.now()
        );

        repository.updateUnit(
                updatedUnit.getId(),
                updatedUnit
        );

        return "Successful Update";
    }

    public Units.ReturnGetUnitsRequest userIdGetUnits (
            GetUnitRequest requestPayload){
        User getUser =
                userRepository.findById(
                requestPayload.getUserId()).orElseThrow(
                ()->throwUnitsError("User not found"));

        Units targetUnit = repository.getUnitByUserId(
                getUser).orElseThrow(
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
