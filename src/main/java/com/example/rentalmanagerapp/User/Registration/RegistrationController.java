package com.example.rentalmanagerapp.User.Registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String createAccount(){
        return registrationService.createAccount();
    }

}
