package com.example.rentalmanagerapp.rental.unitcode;

import com.example.rentalmanagerapp.rental.unitcode.requests.UpdateCodeRequest;
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

    @PatchMapping("/updateCode")
    public String resendCode(
            @RequestBody UpdateCodeRequest updateCodePayload){
        return unitCodesService.updateCode(updateCodePayload);
    }

    @DeleteMapping("/delete")
    public String removeUnitCode(
            @RequestBody Long unitId){
        return unitCodesService.deleteUnitCode(unitId);
    }

}
