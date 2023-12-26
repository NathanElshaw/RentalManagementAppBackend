package com.example.rentalmanagerapp.rental.rentee.charges;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChargesService {

    private final ChargesRepository repository;

    private IllegalStateException chargeNotFound(){
        return new IllegalStateException("Charge not found");
    }

    public String createCharge(
            Charges.createChargeRequest chargeRequest){
        return "";
    }

    public String updateCharge(
            Charges chargeRequest
    ){
        repository
                .findByChargeId(chargeRequest.getChargeId())
                .orElseThrow(this::chargeNotFound);


        return"";
    }

    public String deleteCharge (Long chargeId){
        Charges targetCharge = repository
                .findById(chargeId)
                .orElseThrow(this::chargeNotFound);

        repository.delete(targetCharge);

        return "Successfully removed";
    }

}
