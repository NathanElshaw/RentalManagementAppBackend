package com.example.rentalmanagerapp.Rental;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/create")
    public String createRental(@RequestBody RentalRequest rentalRequest){
        //Todo check priv, add service, validate jwt
        return rentalService.createRental(rentalRequest);
    }

    @PutMapping("/update")
    public String updateRental(){
        //Todo check priv, add service, validate
        return "";
    }

    @PutMapping("/addRenter")
    public String addRenter(){
        //Todo Admin version of joinRental
        return "";
    }

    @DeleteMapping("/delete")
    public String deleteRental(){
        //Todo check priv, add service, validate
        return "";
    }

    //Base User Api interation

    //User Rental api

    @GetMapping("/joinRental")
    public String joinRental(@RequestParam("unitCode") String unitCode){
        return rentalService.joinRental(unitCode);
    }
}
