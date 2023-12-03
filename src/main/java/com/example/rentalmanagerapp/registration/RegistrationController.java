package com.example.rentalmanagerapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/register")
public class RegistrationController {
    private RegistrationService registrationService;

    @PostMapping
    public String createAccount(
            @RequestBody RegistrationRequest registrationPayload){
        return registrationService.createAccount(registrationPayload);
    }

    @GetMapping
    public String sendToken(
            @RequestParam("email") String email){
        return registrationService.sendToken(email);
    }

    @PutMapping
    public String confirmToken(
            @RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
