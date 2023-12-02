package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Units.Requests.GetUnitRequest;
import com.example.rentalmanagerapp.Rental.Units.Requests.ReturnGetUnitsRequest;
import com.example.rentalmanagerapp.Rental.Units.Requests.UnitsRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/units")
public class UnitsController {

    private final UnitsService unitsService;

    @PostMapping("/create")
    public String createUnit(@RequestBody UnitsRequest createUnitPayload){
        return unitsService.createUnit(createUnitPayload);
    }

    @GetMapping("/getRental")
    public String getRental (@RequestBody UnitsRequest.GetRentalRequest getRentalPayload){
        return unitsService.getRentalWithCode(getRentalPayload);
    }

    @PatchMapping ("/update")
    public String updateUnit (@RequestBody Units updateUnitsPayload){
        return unitsService.updateUnit(updateUnitsPayload);
    }

    //Admin Requests
    @GetMapping("/getAllUnitByAddress")
    public List<Units> getAllUnitsByAddress(@RequestParam("Address") String payloadAddress){
        return unitsService.getAllUnitsByAddress(payloadAddress);
    }

    //User Requests
    @GetMapping("/userIdGetRental")
    public Units.ReturnGetUnitsRequest userIdGetUnits(@RequestBody GetUnitRequest userId){
        return unitsService.userIdGetUnits(userId);
    }
}
