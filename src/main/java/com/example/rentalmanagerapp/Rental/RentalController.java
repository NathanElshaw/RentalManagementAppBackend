package com.example.rentalmanagerapp.Rental;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    @PostMapping("/create")
    public String createRental(){
        //Todo check priv, add service, validate jwt
        return "";
    }

    @PutMapping("/update")
    public String updateRental(){
        //Todo check priv, add service, validate
        return "";
    }

    @DeleteMapping("/delete")
    public String deleteRental(){
        //Todo check priv, add service, validate
        return "";
    }
}
