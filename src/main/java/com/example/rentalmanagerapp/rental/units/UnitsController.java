package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.units.requests.UnitsRequest;
import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public UnitsDTO getRental (
            @RequestBody String unitCode){
        return unitsService.getRentalWithCode(unitCode);
    }

    @PatchMapping ("/update")
    public String updateUnit (
            @RequestBody Units updateUnitsPayload){
        return unitsService.updateUnit(updateUnitsPayload);
    }

    @DeleteMapping("/delete")
    public String deleteUnit(
            @RequestBody Units unit){
        return unitsService.deleteUnit(unit);
    }

    //Admin Requests
    @GetMapping("/getAllUnitsByAddress")
    public List<UnitsDTO> getAllUnitsByAddress(
            @RequestParam("Address") String payloadAddress){
        return unitsService.getAllUnitsByAddress(payloadAddress);
    }

    @GetMapping("/getAllUnits")
    public List<Units.GetAllUnitsWithDetails> getAllUnitsWithDetails(){

        return unitsService.getAllUnitsWithDetails();
    }
}
