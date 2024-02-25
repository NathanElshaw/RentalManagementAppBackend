package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.units.UnitsDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public UnitsDTO getUnitWithCode (
            @RequestParam("Code") String code){
        return unitCodesService.getUnitWithCode(code);
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
