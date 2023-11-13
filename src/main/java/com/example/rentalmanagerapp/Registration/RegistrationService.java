package com.example.rentalmanagerapp.Registration;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private EmailValidator email;

    public String createAccount(RegistrationRequest request){
        boolean emailIsValid = email.validate(request.getEmail());

        if(!emailIsValid || request.getEmail() == null){
            throw new IllegalStateException("Email is not valid");
        } else if(email.validateAgainstDb(request.getEmail()) != true){
            throw new IllegalStateException("Email already exists");
        }
        return "";
    }

}
