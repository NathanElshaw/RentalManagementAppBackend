package com.example.rentalmanagerapp.Registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String createAccount(@RequestBody RegistrationRequest registrationPayload){
        return registrationService.createAccount(registrationPayload);
    }

}
