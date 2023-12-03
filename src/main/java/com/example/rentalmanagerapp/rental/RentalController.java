package com.example.rentalmanagerapp.rental;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/create")
    public String createRental(
            @RequestBody RentalRequest rentalRequest
    ){
        //Todo check priv, add service, validate jwt
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/getAll")
    public List<Rental> getAllUnits(){
        return rentalService.getAllRentals();
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
}
