package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChargesService {

    private final ChargesRepository repository;

    private BadRequestException chargeNotFound(){
        return new BadRequestException("Charge not found");
    }

    public String createCharge(
            Charges charges){
        repository.save(charges);
        return "";
    }

    public String updateCharge(
            Charges charge){
        boolean chargeExists = repository
                .assertChargeExists(charge);

        if(!chargeExists){
            throw chargeNotFound();
        }

        repository.updateCharge(charge.getId(), charge);

        return "Successfully updated";
    }

    public String deleteCharge (Charges charge){
        boolean chargeExists = repository
                .assertChargeExists(charge);

        if(!chargeExists){
            throw chargeNotFound();
        }

        repository.delete(charge);

        return "Successfully Deleted";
    }

}
