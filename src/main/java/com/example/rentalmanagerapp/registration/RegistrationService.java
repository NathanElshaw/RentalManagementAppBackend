package com.example.rentalmanagerapp.Registration;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private EmailValidator email;

    public String createAccount(RegistrationRequest request){
        boolean emailIsValid = email.validate(request.getEmail());

        if(!emailIsValid || request.getEmail() == null){
            throw new IllegalStateException("Email is not valid");
        } else if(!email.validateAgainstDb(request.getEmail())){
            throw new IllegalStateException("Email already exists");
        }
        return "";
    }

    public String sendToken(String email){
        return "";
    }

    public String confirmToken(String token){
        return "";
    }

}
