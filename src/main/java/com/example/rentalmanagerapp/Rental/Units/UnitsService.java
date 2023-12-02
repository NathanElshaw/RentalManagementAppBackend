package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.RentalRepository;
import com.example.rentalmanagerapp.Rental.Units.Requests.GetUnitRequest;
import com.example.rentalmanagerapp.Rental.Units.Requests.UnitsRequest;
import com.example.rentalmanagerapp.User.User;
import com.example.rentalmanagerapp.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UnitsService {

    private final UserRepository userRepository;
    private final UnitsRepository unitsRepository;
    private final RentalRepository rentalRepository;

    public String createUnit(UnitsRequest createUnitPayload){
        boolean doesExist = unitsRepository.findByUnitAddressAndUnitNumber(createUnitPayload.getUnitNumber(),
                createUnitPayload.getUnitAddress()).isPresent();

        if(doesExist){
            throw new IllegalStateException("Unit Already Exists");
        }

        Rental parentRental = rentalRepository.findByRentalAddress(createUnitPayload.getUnitAddress()).orElseThrow(()->
                new IllegalStateException("Parent Rental doesnt exist"));

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

        rentalRepository.updateRentalOnNewUnit(createUnitPayload.getUnitAddress(),
                ((parentRental.getAvgRentAmount() * parentRental.getTotalUnits() )+ createUnitPayload.getRentAmount())/ (parentRental.getTotalUnits() + 1),
                parentRental.getTotalRentIncome() + createUnitPayload.getRentAmount(),
                parentRental.getTotalUnits() + 1
                );

        unitsRepository.save(newUnit);

        return "Success";
    }

    public List<Units> getAllUnitsByAddress(String payloadAddress){
        List<Units> returnUnitsList = new ArrayList<>();

        List<Units> returnedUnits = unitsRepository.getAllUnitByAddress(payloadAddress).orElseThrow(
                ()-> new IllegalStateException("No units at that address")
        );

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
                        rental.getLeaseEnd()
                );
                returnUnitsList.add(newUnit);
            });

        return returnUnitsList;
    }

    public String getRentalWithCode(UnitsRequest.GetRentalRequest addRenterPayload){
        //Todo Validate unitCode then return unit to user to confirm if its the correct one.
        return "";
    }

    public String updateUnit(Units updateUnitPayload){
        return "";
    }

    //UserRequests

    public Units.ReturnGetUnitsRequest userIdGetUnits (GetUnitRequest requestPayload){
        User getUser = userRepository.findById(requestPayload.getUserId()).orElseThrow(
                ()->new IllegalStateException("User not found")
        );
        Units targetUnit = unitsRepository.getUnitByUserId(getUser).orElseThrow(
                ()->new IllegalStateException("User is not apart of any units")
        );

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
                targetUnit.getLeaseEnd()
        );
    }

}
