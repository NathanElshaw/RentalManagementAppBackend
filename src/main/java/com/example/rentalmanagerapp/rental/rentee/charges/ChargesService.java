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

}
