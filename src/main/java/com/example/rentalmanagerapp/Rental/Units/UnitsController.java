package com.example.rentalmanagerapp.Rental.Units;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/units")
public class UnitsController {

    private final UnitsService unitsService;

    @PostMapping("/create")
    public String createUnit(@RequestBody UnitsRequest createUnitPayload){
        return unitsService.createUnit(createUnitPayload);
    }
}
