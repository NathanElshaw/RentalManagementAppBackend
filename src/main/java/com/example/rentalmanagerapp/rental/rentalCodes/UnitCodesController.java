package com.example.rentalmanagerapp.rental.rentalCodes;

import com.example.rentalmanagerapp.rental.rentalCodes.requests.JoinUnitRequest;
import com.example.rentalmanagerapp.rental.rentalCodes.requests.UnitCodesRequest;
import com.example.rentalmanagerapp.rental.rentalCodes.requests.UpdateCodeRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/unitCodes")
public class UnitCodesController {

    private final UnitCodesService unitCodesService;

    @PostMapping("/createUnitCode")
    public String createUnitCodes (@RequestBody UnitCodesRequest unitCodePayload){
        return unitCodesService.createUnitCode(unitCodePayload);
    }

    @PatchMapping("/joinUnit")
    public String joinUnit(@RequestBody JoinUnitRequest joinUnitPayload){
        return unitCodesService.joinUnit(joinUnitPayload);
    }

    @PatchMapping("/updateCode")
    public String resendCode(@RequestBody UpdateCodeRequest updateCodePayload){
        return unitCodesService.updateCode(updateCodePayload);
    }

    @DeleteMapping("/removeUnitCode")
    public String removeUnitCode(@RequestBody Long unitId){
        return unitCodesService.deleteUnitCode(unitId);
    }

}
