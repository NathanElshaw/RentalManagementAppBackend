package com.example.rentalmanagerapp.Registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String createAccount(@RequestBody RegistrationRequest registrationPayload){
        return registrationService.createAccount(registrationPayload);
    }

    @PutMapping
    public String confirmToken(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
