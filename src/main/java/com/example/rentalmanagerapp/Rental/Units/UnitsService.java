package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.RentalRepository;
import com.example.rentalmanagerapp.Rental.Units.Requests.GetUnitRequest;
import com.example.rentalmanagerapp.Rental.Units.Requests.UnitsRequest;
import com.example.rentalmanagerapp.User.User;
import com.example.rentalmanagerapp.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        unitsRepository.save(newUnit);

        return "Success";
    }

    public String getRentalWithCode(UnitsRequest.GetRentalRequest addRenterPayload){
        //Todo Validate unitcode then return unit to user to confirm if its the correct one.
        return "";
    }

    public String updateUnit(Units updateUnitPayload){
        return "";
    }

    //UserRequests

    public Units userIdGetUnits (GetUnitRequest requestPayload){
        User getUser = userRepository.findById(requestPayload.getUserId()).orElseThrow(
                ()->new IllegalStateException("User not found")
        );
        Units targetUnit = unitsRepository.getUnitByUserId(getUser).orElseThrow(
                ()->new IllegalStateException("User is not apart of any units")
        );

        Units returnUnit = new Units(
                targetUnit.getUnitNumber(),
                targetUnit.getBeds(),
                targetUnit.getBaths(),
                targetUnit.getUnitAddress(),
                targetUnit.getHasPets(),
                targetUnit.getRentAmount(),
                targetUnit.getRentDueDate().toString(),
                targetUnit.getLeaseStart().toString(),
                targetUnit.getLeaseEnd().toString(),
                targetUnit.getParentUnitId()
        );

        return returnUnit;
    }

}
