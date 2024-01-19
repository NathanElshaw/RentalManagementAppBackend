package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/charges")
@AllArgsConstructor
public class ChargesController {

    private final ChargesService chargesService;


    @PostMapping("/create")
    public String createCharge(
            @RequestBody Charges chargesPayload){
        return chargesService.createCharge(chargesPayload);
    }

    @GetMapping("/user/getCharges")
    public List<Charges> getUserCharges(User user){
        return new ArrayList<Charges>();
    }

    @PatchMapping("/user/pay")
    public String makePayment(){
        return "";
    }

    @PatchMapping("/update")
    public String updateCharge(@RequestBody Charges charge){
        return  chargesService.updateCharge(charge);
    }

    @DeleteMapping("/delete")
    public String deleteCharge(
            @RequestBody Charges charge) {
        return chargesService.deleteCharge(charge);
    }

}
