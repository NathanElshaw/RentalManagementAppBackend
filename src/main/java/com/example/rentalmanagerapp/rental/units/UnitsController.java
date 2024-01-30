package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.units.requests.UnitsRequest;
import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/units")
public class UnitsController {
    private final UnitsService unitsService;

    @PostMapping("/create")
    public String createUnit(
            @RequestBody Units createUnitPayload){
        return unitsService.createUnit(createUnitPayload);
    }

    @GetMapping("/getRental")
    public Units getRental (
            @RequestBody String unitCode){
        return unitsService.getRentalWithCode(unitCode);
    }

    @PatchMapping ("/update")
    public String updateUnit (
            @RequestBody Units updateUnitsPayload){
        return unitsService.updateUnit(updateUnitsPayload);
    }

    //Admin Requests
    @GetMapping("/getAllUnitsByAddress")
    public List<Units> getAllUnitsByAddress(
            @RequestParam("Address") String payloadAddress){
        return unitsService.getAllUnitsByAddress(payloadAddress);
    }

    @GetMapping("/getAllUnits")
    public List<Units.GetAllUnitsWithDetails> getAllUnitsWithDetails(){

        return unitsService.getAllUnitsWithDetails();
    }

    //User Requests
    @GetMapping("/userIdGetRental")
    public Units.ReturnGetUnitsRequest userIdGetUnits(
            @RequestBody User user){
        return unitsService.userIdGetUnits(user);
    }
}
