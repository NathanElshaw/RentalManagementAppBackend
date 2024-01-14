package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestBody Rental rentalRequest
    ){
        //Todo check priv, add service, validate jwt
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/getAll")
    public List<Rental> getAllUnits(){
        return rentalService.getAllRentals();
    }

    @GetMapping("/manager/getUnits")
    public List<Rental> getPropertyManagerUnits(
            @RequestBody User user){
        return rentalService.getPropertyMangerRentals(user);
    }

    @PatchMapping("/update")
    public String updateRental(
            @RequestBody Rental updatePayload){
        return rentalService.updateRental(updatePayload);
    }

    @DeleteMapping("/delete")
    public String deleteRental(
            @RequestBody Rental rental){
        return rentalService.deleteRental(rental);
    }
}
