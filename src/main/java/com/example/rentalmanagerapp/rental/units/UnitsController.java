package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.units.requests.GetUnitRequest;
import com.example.rentalmanagerapp.rental.units.requests.UnitsRequest;
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

    @GetMapping("/getAllUnitDetailed")
    public List<Units.GetAllUnitsWithDetails> getAllUnitsWithDetails(){
        return unitsService.getAllUnitsWithDetails();
    }

    //User Requests
    @GetMapping("/userIdGetRental")
    public Units.ReturnGetUnitsRequest userIdGetUnits(@RequestBody GetUnitRequest userId){
        return unitsService.userIdGetUnits(userId);
    }
}
