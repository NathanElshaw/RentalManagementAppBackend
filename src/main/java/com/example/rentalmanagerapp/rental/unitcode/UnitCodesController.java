package com.example.rentalmanagerapp.rental.unitcode;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/unitCodes")
public class UnitCodesController {
    private final UnitCodesService unitCodesService;

    @PostMapping("/create")
    public String createUnitCodes (
            @RequestBody UnitCodes unitCodes){
        return unitCodesService.createUnitCode(unitCodes);
    }

    @PatchMapping("/update")
    public String resendCode(
            @RequestBody UnitCodes updateCode){
        return unitCodesService.updateCode(updateCode);
    }

    @DeleteMapping("/delete")
    public String removeUnitCode(
            @RequestBody Long unitId){
        return unitCodesService.deleteUnitCode(unitId);
    }

}
