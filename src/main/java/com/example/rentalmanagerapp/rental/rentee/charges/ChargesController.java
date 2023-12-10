package com.example.rentalmanagerapp.rental.rentee.charges;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/charges")
@AllArgsConstructor
public class ChargesController {

    private final ChargesService chargesService;


    @PostMapping("/create")
    public String createCharge(
            @RequestBody Charges.createChargeRequest chargesPayload){
        return chargesService.createCharge(chargesPayload);
    }

    @DeleteMapping("/delete")
    public String deleteCharge(
            @RequestBody Long chargeId) {
        return chargesService.deleteCharge(chargeId);
    }

}
