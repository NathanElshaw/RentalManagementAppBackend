package com.example.rentalmanagerapp.rental.rentee.charges;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChargesService {

    private final ChargesRepository chargesRepository;

    public String createCharge(
            Charges.createChargeRequest chargePayload){
        return "";
    }

    public String deleteCharge (Long chargeId){
        Charges targetCharge = chargesRepository.findById(chargeId)
                .orElseThrow(
                        ()->new IllegalStateException("Charge not found"));

        chargesRepository.delete(targetCharge);

        return "Successfully removed";
    }

}
